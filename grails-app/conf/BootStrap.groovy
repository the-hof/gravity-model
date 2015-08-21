import grails.util.Environment
import gravity.Body
import gravity.GravitationalSystem

class BootStrap {

    def init = { servletContext ->
        if (Environment.current == Environment.DEVELOPMENT) {
            seedSampleData()
        }
    }
    def destroy = {
    }

    private seedSampleData() {
        if (Body.count() == 0) {
            GravitationalSystem solar = new GravitationalSystem(
                    name: "Solar System",
                    timeUnit: "seconds",
                    timestepAmount: 3600,
                    distanceUnit: "meters",
                    G: 6.6738e-11
            ).save(flush:true)

            Body sun = new Body(
                    name: "Sun",
                    x: 0, y: 0, z:0,
                    mass: 1.9891e30,
                    vx: 0, vy: 0, vz: 0,
                    radius: 696000000.0,
                    gravitationalSystem: solar
            ).save(flush:true)
            Body earth = new Body (
                    name: "Earth",
                    x: 152e9, y: 0, z:0,
                    mass: 5.9736e24,
                    vx: 0, vy: 0, vz: 0,
                    radius: 6378000.0,
                    gravitationalSystem: solar
            ).save(flush:true)
        }
    }
}
