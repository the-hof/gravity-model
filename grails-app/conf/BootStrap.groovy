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
        seedEarthSunData1()
        seedEarthSunMoonData1()
        seedEarthSmallObjectData1()
        seedEarthSmallObjectData2()
        seedEarthSatelliteData1()
    }

    private seedEarthSatelliteData1() {
        GravitationalSystem earthSystem = new GravitationalSystem(
                name: "Earth System with one second step",
                timeUnit: "seconds",
                timestepAmount: 60,
                distanceUnit: "meters",
                G: 6.6738e-11
        ).save(flush: true, failOnError: true)

        Body earth = new Body(
                name: "Earth",
                x: 0, y: 0, z: 0,
                mass: 5.9736e24,
                vx: 0, vy: 0, vz: 0,
                radius: 6378000.0,
                gravitationalSystem: earthSystem
        ).save(flush: true, failOnError: true)

        Double smallMass = 1
        Double initialHeight = 42164 * 1000
        Body smallObject = new Body(
                x: initialHeight, y: 0, z: 0,
                name: "satellite",
                mass: smallMass,
                vx: 0, vy: 3074, vz: 0,
                radius: 1.0,
                gravitationalSystem: earthSystem
        ).save(flush: true, failOnError: true)
    }

    private seedEarthSmallObjectData1() {
        GravitationalSystem earthSystem = new GravitationalSystem(
                name: "Earth System with one second step",
                timeUnit: "seconds",
                timestepAmount: 1,
                distanceUnit: "meters",
                G: 6.6738e-11
        ).save(flush: true, failOnError: true)

        Body earth = new Body(
                name: "Earth",
                x: 0, y: 0, z: 0,
                mass: 5.9736e24,
                vx: 0, vy: 0, vz: 0,
                radius: 6378000.0,
                gravitationalSystem: earthSystem
        ).save(flush: true, failOnError: true)

        Double smallMass = 1
        Double initialHeight = 6378000 + 490
        Body smallObject = new Body(
                x: initialHeight, y: 0, z: 0,
                name: "Small Object",
                mass: smallMass,
                vx: 0, vy: 10, vz: 0,
                radius: 1.0,
                gravitationalSystem: earthSystem
        ).save(flush: true, failOnError: true)
    }

    private seedEarthSmallObjectData2() {
        GravitationalSystem earthSystem = new GravitationalSystem(
                name: "Earth System with 10 second step",
                timeUnit: "seconds",
                timestepAmount: 10,
                distanceUnit: "meters",
                G: 6.6738e-11
        ).save(flush: true, failOnError: true)

        Body earth = new Body(
                name: "Earth",
                x: 0, y: 0, z: 0,
                mass: 5.9736e24,
                vx: 0, vy: 0, vz: 0,
                radius: 6378000.0,
                gravitationalSystem: earthSystem
        ).save(flush: true, failOnError: true)

        Double smallMass = 1
        Double initialHeight = 6378000 + 490
        Body smallObject = new Body(
                x: initialHeight, y: 0, z: 0,
                name: "Small Object",
                mass: smallMass,
                vx: 0, vy: 10, vz: 0,
                radius: 1.0,
                gravitationalSystem: earthSystem
        ).save(flush: true, failOnError: true)
    }

    private seedEarthSunData1() {
        if (Body.count() == 0) {
            GravitationalSystem solar = new GravitationalSystem(
                    name: "Two Body Solar System",
                    timeUnit: "seconds",
                    timestepAmount: 3600,
                    distanceUnit: "meters",
                    G: 6.6738e-11
            ).save(flush: true)

            Body sun = new Body(
                    name: "Sun",
                    x: 0, y: 0, z: 0,
                    mass: 1.9885e30,
                    vx: 0, vy: 0, vz: 0,
                    radius: 696000000.0,
                    gravitationalSystem: solar
            ).save(flush: true)
            Body earth = new Body(
                    name: "Earth",
                    x: 1.471e11, y: 0, z: 0,
                    mass: 5.97219e24,
                    vx: 0, vy: 30300, vz: 0,
                    radius: 6371000.0,
                    gravitationalSystem: solar
            ).save(flush: true)
        }
    }

    private seedEarthSunMoonData1() {

        GravitationalSystem solar = new GravitationalSystem(
                name: "Three Body Solar System",
                timeUnit: "seconds",
                timestepAmount: 3600,
                distanceUnit: "meters",
                G: 6.6738e-11
        ).save(flush: true)

        Body sun = new Body(
                name: "Sun",
                x: 0, y: 0, z: 0,
                mass: 1.9885e30,
                vx: 0, vy: 0, vz: 0,
                radius: 696000000.0,
                gravitationalSystem: solar
        ).save(flush: true)
        Body earth = new Body(
                name: "Earth",
                x: 1.471e11, y: 0, z: 0,
                mass: 5.97219e24,
                vx: 0, vy: 30300, vz: 0,
                radius: 6371000.0,
                gravitationalSystem: solar
        ).save(flush: true)

        // start moon in negative y direction
        // so motion is in positive x
        // but initializes moving in positive y with earth
        Body moon = new Body(
                name: "Moon",
                x: 1.471e11, y: -36310400, z: 0,
                mass: 0.07342e24,
                vx: 1076, vy: 30300, vz: 0,
                radius: 1737100.0,
                gravitationalSystem: solar
        ).save(flush: true)

    }
}
