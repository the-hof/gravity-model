package gravity

import grails.test.mixin.TestFor
import spock.lang.*

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(GravitationalPhysicsService)
@Mock([Timestep, Body, GravitationalSystem, Force])
class GravitationalPhysicsServiceSpec extends Specification {

    def "should correctly calculate the new velocities and positions of the earth and sun"() {
        given: "the earth and sun"
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
        timestep.body.add(sun)
        timestep.body.add(earth)

        when: "the timestep is advanced"
        def newTimestep = service.advanceTimestep( timestep )

        then: "it should calculate -0.0057458 m/s**2"
        assert Body.count() == 4
        def target = -0.0057458 * solar.timestepAmount
        def the_earth = newTimestep.body.find( {it.name == "Earth"})
        def value = the_earth.vx
        assert value.round(3) == target.setScale(3, BigDecimal.ROUND_HALF_UP)
    }
}
