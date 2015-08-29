package gravity

import grails.transaction.Transactional

@Transactional
class GravitationalPhysicsService {

    def Timestep advanceTimestep(Timestep currentTimestep) {
        def calculatedTimestep = new Timestep(
                number: currentTimestep.number + 1,
                gravitationalSystem: currentTimestep.gravitationalSystem
        ).save(failOnError: true, flush: true)

        def lastPositions = Body.where {
            timestep == currentTimestep
        }.list().sort( {it.name} )

        //calculate forces on all bodies
        def force_matrix = gravitationFunction(lastPositions, currentTimestep, "CALCULATED")

        HashSet<Body> expectedLocations = new HashSet<Body>();

        for (def i = 0; i < lastPositions.size(); i++) {
            Vector sumOfForces = VectorCalculator.SumVectors(force_matrix[i])
            Body newPosition = calculateNewPosition(sumOfForces, lastPositions[i], calculatedTimestep)

            expectedLocations.add(newPosition)
        }

        //now recalculate the forces based on the new positions
        def perturb_force_matrix = gravitationFunction(expectedLocations.toList(), currentTimestep, "AVERAGED")

        HashSet<Body> newLocations = new HashSet<Body>()

        for (def i = 0; i < lastPositions.size(); i++) {
            Vector sumOfForces = VectorCalculator.SumVectors(force_matrix[i])
            Vector sumOfPerturbs = VectorCalculator.SumVectors(perturb_force_matrix[i])
            Vector avgVector = VectorCalculator.AverageVectors(sumOfForces, sumOfPerturbs)

            Body newPosition = calculateNewPosition(avgVector, lastPositions[i], calculatedTimestep)
            newPosition.save(failOnError: true, flush: true)
            newLocations.add(newPosition)
        }

        def listLocations = newLocations.toList().sort( {it.name} )
        calculatedTimestep.body = new HashSet<Body>()

        for (int i=0; i<newLocations.size(); i++) {
            calculatedTimestep.body.add(newLocations[i])

            for (int j=0; j<newLocations.size(); j++) {
                if (i != j) {
                    Force calcedForce = new Force (
                            timestep: calculatedTimestep,
                            thisBody: listLocations[i],
                            causingBody: listLocations[j],
                            type: "INITIAL",
                            magnitude: force_matrix[i][j].magnitude,
                            fx: force_matrix[i][j].x,
                            fy: force_matrix[i][j].y,
                            fz: force_matrix[i][j].z
                    ).save(failOnError:true, flush:true)

                    Force avgedForce = new Force (
                            timestep: calculatedTimestep,
                            thisBody: listLocations[i],
                            causingBody: listLocations[j],
                            type: "AVERAGED",
                            magnitude: perturb_force_matrix[i][j].magnitude,
                            fx: perturb_force_matrix[i][j].x,
                            fy: perturb_force_matrix[i][j].y,
                            fz: perturb_force_matrix[i][j].z
                    ).save(failOnError:true, flush:true)
                }
            }
        }

        return calculatedTimestep
    }

    private Body calculateNewPosition(Vector sumOfForces, Body affectedBody, Timestep timestep) {

        Double mass = affectedBody.mass

        def t = timestep.gravitationalSystem.timestepAmount

        Vector new_velocity = calculateNewVelocity(sumOfForces, mass, t, affectedBody)
        Vector new_position = calculateNewPosition(sumOfForces, mass, t, affectedBody)

        Body newPosition = new Body(
                gravitationalSystem: affectedBody.gravitationalSystem,
                mass: affectedBody.mass,
                radius: affectedBody.radius,
                name: affectedBody.name,
                timestep: timestep,
                x: new_position.x, y: new_position.y, z: new_position.z,
                vx: new_velocity.x, vy: new_velocity.y, vz: new_velocity.z
        )

        return newPosition
    }

    private Vector[][] gravitationFunction(List<Body> positions, Timestep timestep, String calcType) {
        def i, j
        def force_matrix = new Vector[positions.size()][positions.size()]

        positions = positions.sort( { it.name })

        for (i = 0; i < positions.size(); i++) {
            force_matrix[i][i] = new Vector(
                    magnitude: 0, x: 0, y: 0, z: 0
            )

            for (j = positions.size() - 1; j > i; j--) {
                Body thisObject = positions[i]
                Body thatObject = positions[j]
                Double G = timestep.gravitationalSystem.G
                Vector calculatedGravity = GravitationalForceCalculator.CalculateGravitationalForce(
                        G, thisObject, thatObject, timestep, calcType
                )

                Vector calculatedOpposite = new Vector(
                        magnitude: calculatedGravity.magnitude,
                        x: (-1) * calculatedGravity.x,
                        y: (-1) * calculatedGravity.y,
                        z: (-1) * calculatedGravity.z
                )

                force_matrix[i][j] = calculatedGravity
                force_matrix[j][i] = calculatedOpposite
            }
        }

        return force_matrix
    }

    // v_next = (Fnet / mass) * t + v_prev
    private Vector calculateNewVelocity(Vector sumOfForces, Double mass, Integer t, Body b) {
        assert mass != 0
        def new_vx = (sumOfForces.x/mass) * t
        def new_vy = (sumOfForces.y/mass)  * t
        def new_vz = (sumOfForces.z/mass) * t
        new_vx += b.vx
        new_vy += b.vy
        new_vz += b.vz

        def result = new Vector(
                x: new_vx,
                y: new_vy,
                z: new_vz
        )
        result.setMagnitude()
        return result
    }

    //x_next = x_prev + old_vx * t + (1/2) (Fnet / mass) * t**2
    private Vector calculateNewPosition(Vector sumOfForces, Double mass, Integer t, Body b) {
        assert mass != 0

        def new_x = b.x
        def new_y = b.y
        def new_z = b.z
        new_x += b.vx * t
        new_y += b.vy * t
        new_z += b.vz * t
        new_x += ((sumOfForces.x/mass) * t**2)/2
        new_y += ((sumOfForces.y/mass) * t**2)/2
        new_z += ((sumOfForces.z/mass) * t**2)/2

        def result = new Vector(
                x: new_x,
                y: new_y,
                z: new_z
        )
        result.setMagnitude()
        return result
    }
}
