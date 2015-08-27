package gravity

import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import spock.lang.*

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestMixin(GrailsUnitTestMixin)
class GravitationalForceCalculatorSpec extends Specification {

    def "should calculate the weight of an object on the earth's surface"() {
        given:  "the earth and a small object"
        Body earth = new Body (
                x:0, y:0, z:0,

                        name: "Earth",
                        mass: 5.97219e24

        )

        Integer smallMass = 1
        Body smallObject = new Body (
                x:6371000, y:0, z:0,
                name: "Small Object",
                        mass: smallMass
        )
        Double G = 6.6738e-11

        when: "the force due to gravity is calculated"
        Force gravity = GravitationalForceCalculator.CalculateGravitationalForce(G,
                earth,
                smallObject,
                new Timestep(),
                "TEST"
        )

        then: "it should be 9.8 * the mass of the small object"
        assert (Math.abs(gravity.magnitude)* smallMass).round(3) == 9.820
    }

    def "should calculate the weight of an object on a nontrivial point on the earth's surface"() {
        given:  "the earth and a small object"
        Body earth = new Body (
                x:0, y:0, z:0,

                        name: "Earth",
                        mass: 5.97219e24

        )

        Integer smallMass = 1
        Body smallObject = new Body (
                x:1000, y:-3000000, z:5620466,

                        name: "Small Object",
                        mass: smallMass

        )
        Double G = 6.6738e-11

        when: "the force due to gravity is calculated"
        Force gravity = GravitationalForceCalculator.CalculateGravitationalForce(G,
                earth,
                smallObject,
                new Timestep(),
                "TEST"
        )

        then: "it should be 9.8 * the mass of the small object"
        assert (Math.abs(gravity.magnitude)* smallMass).round(3) == 9.820
    }
}
