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
}
