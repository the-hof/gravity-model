package gravity
import grails.converters.JSON

class TimestepController {
    def gravitationalPhysicsService

    def index() {
        def systemId = params.GravitationalSystemId
        def timesteps = Timestep.findAll {
            gravitationalSystem == GravitationalSystem.get(systemId)
        }

        def result = [timesteps: timesteps]
        render result as JSON
    }

    def create() {
        def stellarSystem = GravitationalSystem.get(params.GravitationalSystemId)
        def timesteps = Timestep.where {
            gravitationalSystem == stellarSystem
        }.list(max: 1, sort: "number", order: "desc")

        def lastTimestep

        if (timesteps.size() == 0) {
            def initial_bodies = Body.findAll {
                gravitationalSystem == stellarSystem
            }

            lastTimestep = new Timestep(
                    gravitationalSystem: stellarSystem,
                    number: 0
            ).save(failOnError: true, flush: true)

            initial_bodies.each {
                def newBodyLocation = new Body(
                        name: it.name,
                        mass: it.mass,
                        radius: it.radius,
                        x: it.x,
                        y: it.y,
                        z: it.z,
                        vx: it.vx,
                        vy: it.vy,
                        vz: it.vz,
                        gravitationalSystem: stellarSystem,
                        timestep: lastTimestep
                ).save(failOnError: true, flush:true)

                if (!lastTimestep.body) lastTimestep.body = new HashSet<Body>()
                lastTimestep.body.add(newBodyLocation)
            }

        } else {
            lastTimestep = Timestep.where {
                gravitationalSystem == stellarSystem
            }.list(max: 1, sort: "number", order: "desc")
        }

        def newTimestep = gravitationalPhysicsService.advanceTimestep( lastTimestep )
        def result = [timesteps: newTimestep]
        render result as JSON
    }
}
