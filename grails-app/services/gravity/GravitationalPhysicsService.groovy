package gravity

import grails.transaction.Transactional

@Transactional
class GravitationalPhysicsService {

    def Timestep advanceTimestep(Timestep currentTimestep) {
        def newTimestep = new Timestep(
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
                thisObject.timestep = newTimestep
                Double G = currentTimestep.gravitationalSystem.G
                Force gravity = GravitationalForceCalculator.GetGravitationalForce(G, thisObject, thatObject)
                Force opposite = new Force(
                        timestep: newTimestep,
                        thisBody: thatObject,
                        causingBody: thisObject,
                        magnitude: gravity.magnitude,
                        fx: (-1) * gravity.fx,
                        fy: (-1) * gravity.fy,
                        fz: (-1) * gravity.fz
                )

                force_matrix[i][j] = new Vector(
                        magnitude: gravity.magnitude, x: gravity.fx, y: gravity.fy, z: gravity.fz
                )
                force_matrix[j][i] = new Vector(
                        magnitude: gravity.magnitude, x: gravity.fx, y: gravity.fy, z: gravity.fz
                )

                gravity.save(failOnError: true, flush: true)
                opposite.save(failOnError: true, flush: true)
            }

            // calculate new velocities
            Vector sumOfForces = VectorCalculator.SumVectors(force_matrix[i])
            Double mass = lastPositions[i].mass

            // a = Fnet / mass

            // v_next = (Fnet / mass) * t + v_prev
            def t = currentTimestep.gravitationalSystem.timestepAmount

            def new_vx = (sumOfForces.x/mass) * t
            def new_vy = (sumOfForces.y/mass)  * t
            def new_vz = (sumOfForces.z/mass) * t
            new_vx += lastPositions[i].vx
            new_vy += lastPositions[i].vy
            new_vz += lastPositions[i].vz

            //x_next = x_prev + old_vx * t + (1/2) (Fnet / mass) * t**2
            def new_x = lastPositions[i].x
            def new_y = lastPositions[i].y
            def new_z = lastPositions[i].z
            new_x += lastPositions[i].vx * t
            new_y += lastPositions[i].vy * t
            new_z += lastPositions[i].vz * t
            new_x += ((sumOfForces.x/mass) * t**2)/2
            new_y += ((sumOfForces.y/mass) * t**2)/2
            new_z += ((sumOfForces.z/mass) * t**2)/2

            Body newPosition = new Body(
                    gravitationalSystem: lastPositions[i].gravitationalSystem,
                    mass: lastPositions[i].mass,
                    radius: lastPositions[i].radius,
                    name: lastPositions[i].name,
                    timestep: newTimestep,
                    x: new_x, y: new_y, z: new_z,
                    vx: new_vx, vy: new_vy, vz:new_vz
            ).save(failOnError: true, flush: true)

            if (!newTimestep.body) newTimestep.body = new HashSet<Body>()
            newTimestep.body.add(newPosition)
        }

        return newTimestep
    }
}
