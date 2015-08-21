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
                        mass: 5.9736e24

        )

        Integer smallMass = 1
        Body smallObject = new Body (
                x:6378000, y:0, z:0,
                name: "Small Object",
                        mass: smallMass
        )
        Double G = 6.6738e-11

        when: "the force due to gravity is calculated"
        Force gravity = GravitationalForceCalculator.GetGravitationalForce(G, earth, smallObject)

        then: "it should be 9.8 * the mass of the small object"
        assert (Math.abs(gravity.magnitude)* smallMass).round(3) == 9.800
    }

    def "should calculate the weight of an object on a nontrivial point on the earth's surface"() {
        given:  "the earth and a small object"
        Body earth = new Body (
                x:0, y:0, z:0,

                        name: "Earth",
                        mass: 5.9736e24

        )

        Integer smallMass = 1
        Body smallObject = new Body (
                x:1000, y:-3000000, z:5628399.68,

                        name: "Small Object",
                        mass: smallMass

        )
        Double G = 6.6738e-11

        when: "the force due to gravity is calculated"
        Force gravity = GravitationalForceCalculator.GetGravitationalForce(G, earth, smallObject)

        then: "it should be 9.8 * the mass of the small object"
        assert (Math.abs(gravity.magnitude)* smallMass).round(3) == 9.800
    }
}
