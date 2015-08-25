package gravity

import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import spock.lang.*

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestMixin(GrailsUnitTestMixin)
class VectorCalculatorSpec extends Specification {
    def "should add two vectors"() {
        given:  "Two vectors and an object"
        def force_matrix = new Vector[2]
        force_matrix[0] = new Vector(x: 1, y: 5, z: 3)
        force_matrix[1] = new Vector(x: 10, y: 20, z: 50)

        when: "are added together"
        Vector totalForce = VectorCalculator.SumVectors(force_matrix)

        then: "they should give the correct result"
        assert totalForce.x == 11
        assert totalForce.y == 25
        assert totalForce.z == 53
    }

    def "should add five vectors"() {
        given: "Five vectors and an object"
        def force_matrix = new Vector[5]
        force_matrix[0] = new Vector(x: 1, y: 5, z: 3)
        force_matrix[1] = new Vector(x: 10, y: 20, z: 50)
        force_matrix[2] = new Vector(x: 200, y: 400, z: 300)
        force_matrix[3] = new Vector(x: 1000, y: 5000, z: 3000)
        force_matrix[4] = new Vector(x: 1, y: 1, z: 1)

        when: "are added together"
        Vector totalForce = VectorCalculator.SumVectors(force_matrix)

        then: "they should give the correct result"
        assert totalForce.x == 1212
        assert totalForce.y == 5426
        assert totalForce.z == 3354
    }

    def "should add two opposite vectors"() {
        given: "Two vectors and an object"
        def force_matrix = new Vector[2]
        force_matrix[0] = new Vector(x: 1, y: 5, z: 3)
        force_matrix[1] = new Vector(x: -1, y: -5, z: -3)

        when: "are added together"
        Vector totalForce = VectorCalculator.SumVectors(force_matrix)

        then: "they should give the correct result"
        assert totalForce.x == 0
        assert totalForce.y == 0
        assert totalForce.z == 0
    }

    def "should correctly calculate its own magnitude"() {
        given: "A vector"
        def v_before = new Vector(x: 2, y: 9, z:17)

        when: "the vector calculates its magnitude"
        v_before.setMagnitude()

        then: "it comes up with the correct value"
        assert v_before.magnitude == Math.sqrt(2**2 + 9**2 + 17**2)
    }

    def "should scale vector to a unit vector"() {
        given: "A vector"
        Double magnitude = Math.sqrt(2**2 + 9**2 + 17**2)
        def v_before = new Vector(x: 2, y: 9, z:17, magnitude: magnitude)

        when: "is scaled"
        def v_after = v_before.getUnitVector()

        then: "the x, y, and z values should be scaled to a unit length"
        assert v_after.x.round(5) == (v_before.x/magnitude).round(5)
        assert v_after.y.round(5) == (v_before.y/magnitude).round(5)
        assert v_after.z.round(5) == (v_before.z/magnitude).round(5)
        assert (v_after.x**2 + v_after.y**2 + v_after.z ** 2).round(5) == 1
    }

    def "should correctly multiply a vector by a scalar"() {
        given: "A vector"
        def v_before = new Vector(x: 2, y: 9, z:17)

        when: "multiplied by a scalar"
        def v_after = v_before.getScaledVector(-1)

        then: "the x, y, and z values should be scaled"
        assert v_after.x == -2
        assert v_after.y == -9
        assert v_after.z == -17
        assert v_after.magnitude == Math.sqrt(2**2 + 9**2 + 17**2)

    }

    def "should subtract two vectors"() {
        given: "Two vectors"
        def v1 = new Vector(x: 2, y: 9, z:17)
        def v2 = new Vector(x: 3, y: 4, z: 5)

        when:  "subtracting v2-v1"
        def v_after = VectorCalculator.SubtractVectors(v1, v2)

        then: "they should be correctly subtracted"
        assert v_after.x == 1
        assert v_after.y == -5
        assert v_after.z == -12
    }
}
