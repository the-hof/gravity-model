package gravity

import grails.test.mixin.TestFor
import spock.lang.*

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(GravitationalPhysicsService)
@Mock([Timestep, Body, GravitationalSystem, Force])
class GravitationalPhysicsServiceSpec extends Specification {

    //<editor-fold desc="timestep initialization methods">
    private Timestep setupEarthSmallObject(Double initialHeight) {
        GravitationalSystem earthSystem = new GravitationalSystem(
                name: "Earth System",
                timeUnit: "seconds",
                timestepAmount: 1,
                distanceUnit: "meters",
                G: 6.6738e-11
        ).save()

        Body earth = new Body (
                name: "Earth",
                x: 0, y: 0, z:0,
                mass: 5.9736e24,
                vx: 0, vy: 0, vz: 0,
                radius: 6378000.0,
                gravitationalSystem: earthSystem
        ).save()
        Double smallMass = 1
        Body smallObject = new Body(
                x: earth.radius + initialHeight, y: 0, z: 0,
                name: "Small Object",
                mass: smallMass,
                vx: 0, vy: 0, vz: 0,
                radius: 1.0,
                gravitationalSystem: earthSystem
        ).save()

        def timestep = new Timestep (
                gravitationalSystem: earthSystem,
                number: 0
        )
        timestep.body = new HashSet<Body>()

        timestep.body.add(earth)
        timestep.body.add(smallObject)

        return timestep
    }

    private Timestep setupSunEarth() {
        GravitationalSystem solar = new GravitationalSystem(
                name: "Solar System",
                timeUnit: "seconds",
                timestepAmount: 3600,
                distanceUnit: "meters",
                G: 6.6738e-11
        ).save()

        Body sun = new Body(
                name: "Sun",
                x: 0, y: 0, z:0,
                mass: 1.9891e30,
                vx: 0, vy: 0, vz: 0,
                radius: 696000000.0,
                gravitationalSystem: solar
        ).save()
        Body earth = new Body (
                name: "Earth",
                x: 152e9, y: 0, z:0,
                mass: 5.9736e24,
                vx: 0, vy: 0, vz: 0,
                radius: 6378000.0,
                gravitationalSystem: solar
        ).save()

        def timestep = new Timestep (
                gravitationalSystem: solar,
                number: 0
        )
        timestep.body = new HashSet<Body>()
        timestep.body.add(earth)
        timestep.body.add(sun)

        return timestep
    }
    //</editor-fold>

    def "should correctly calculate the new velocities of the earth and a small object on the earth"() {
        given: "the earth and sun"
        def initialHeight = 10
        def timestep = setupEarthSmallObject(10)

        when: "the timestep is advanced"
        def newTimestep = service.advanceTimestep( timestep )

        then: "it should calculate -9.80 m/s**2"
        assert Body.count() == 4
        def target = -9.800 * timestep.gravitationalSystem.timestepAmount
        def the_smallObject = newTimestep.body.find( {it.name == "Small Object"} )
        def change_in_velocity = the_smallObject.vx
        assert change_in_velocity.round(3) == target.setScale(3, BigDecimal.ROUND_HALF_UP)
    }

    def "should correctly calculate the new positions of the earth and a small object on the earth"() {
        given: "the earth and sun"
        def initialHeight = 10
        def timestep = setupEarthSmallObject(10)

        when: "the timestep is advanced"
        def newTimestep = service.advanceTimestep( timestep )

        then: "it should calculate -4.90 m/s**2"
        assert Body.count() == 4
        def target = (-9.800/2.0) * timestep.gravitationalSystem.timestepAmount ** 2
        def initial_position = timestep.body.find( {it.name == "Small Object"} ).x
        def final_position = newTimestep.body.find( {it.name == "Small Object"} ).x
        def change_in_position = final_position - initial_position
        assert change_in_position.round(3) == target.setScale(3, BigDecimal.ROUND_HALF_UP)
    }

    def "should correctly calculate the new velocities of the earth and sun"() {
        given: "the earth and sun"
        def timestep = setupSunEarth()

        when: "the timestep is advanced"
        def newTimestep = service.advanceTimestep( timestep )

        then: "it should calculate -0.0057458 m/s**2"
        assert Body.count() == 4
        def target = -0.0057458 * timestep.gravitationalSystem.timestepAmount
        def the_earth = newTimestep.body.find( {it.name == "Earth"} )
        def change_in_velocity = the_earth.vx
        assert change_in_velocity.round(3) == target.setScale(3, BigDecimal.ROUND_HALF_UP)
    }
}
