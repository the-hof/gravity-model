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
        }.list()

        //calculate forces on all bodies
        def force_matrix = new Vector[lastPositions.size()][lastPositions.size()]
        def i, j

        for (i=0; i<lastPositions.size(); i++) {
            force_matrix[i][i] = new Vector(
                    magnitude: 0, x: 0, y: 0, z: 0
            )

            for (j=lastPositions.size()-1; j>i; j--) {
                Body thisObject = lastPositions[i]
                Body thatObject = lastPositions[j]
                Double G = currentTimestep.gravitationalSystem.G
                Force calculatedGravity = GravitationalForceCalculator.CalculateGravitationalForce(
                        G, thisObject, thatObject, currentTimestep
                )

                Force calculatedOpposite = new Force(
                        timestep: currentTimestep,
                        thisBody: thatObject,
                        causingBody: thisObject,
                        type: "CALCULATED",
                        magnitude: calculatedGravity.magnitude,
                        fx: (-1) * calculatedGravity.fx,
                        fy: (-1) * calculatedGravity.fy,
                        fz: (-1) * calculatedGravity.fz
                )

                calculatedGravity.save(failOnError: true, flush: true)
                calculatedOpposite.save(failOnError: true, flush: true)

                force_matrix[i][j] = calculatedGravity.getForceVector()
                force_matrix[j][i] = calculatedOpposite.getForceVector()
            }

            Vector sumOfForces = VectorCalculator.SumVectors(force_matrix[i])
            Double mass = lastPositions[i].mass

            def t = currentTimestep.gravitationalSystem.timestepAmount

            Vector new_velocity = calculateNewVelocity(sumOfForces, mass, t, lastPositions[i])
            Vector new_position = calculateNewPosition(sumOfForces, mass, t, lastPositions[i])

            Body newPosition = new Body(
                    gravitationalSystem: lastPositions[i].gravitationalSystem,
                    mass: lastPositions[i].mass,
                    radius: lastPositions[i].radius,
                    name: lastPositions[i].name,
                    timestep: calculatedTimestep,
                    x: new_position.x, y: new_position.y, z: new_position.z,
                    vx: new_velocity.x, vy: new_velocity.y, vz: new_velocity.z
            ).save(failOnError: true, flush: true)

            if (!calculatedTimestep.body)
                calculatedTimestep.body = new HashSet<Body>()
            calculatedTimestep.body.add(newPosition)
        }

        return calculatedTimestep
    }

    // v_next = (Fnet / mass) * t + v_prev
    private Vector calculateNewVelocity(Vector sumOfForces, Double mass, Integer t, Body b) {
        def new_vx = (sumOfForces.x/mass) * t
        def new_vy = (sumOfForces.y/mass)  * t
        def new_vz = (sumOfForces.z/mass) * t
        new_vx += b.vx
        new_vy += b.vy
        new_vz += b.vz

        return new Vector(
                x: new_vx,
                y: new_vy,
                z: new_vz
        )
    }

    //x_next = x_prev + old_vx * t + (1/2) (Fnet / mass) * t**2
    private Vector calculateNewPosition(Vector sumOfForces, Double mass, Integer t, Body b) {
        def new_x = b.x
        def new_y = b.y
        def new_z = b.z
        new_x += b.vx * t
        new_y += b.vy * t
        new_z += b.vz * t
        new_x += ((sumOfForces.x/mass) * t**2)/2
        new_y += ((sumOfForces.y/mass) * t**2)/2
        new_z += ((sumOfForces.z/mass) * t**2)/2

        return new Vector(
                x: new_x,
                y: new_y,
                z: new_z
        )
    }
}
