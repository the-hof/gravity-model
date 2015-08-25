package gravity

import grails.test.mixin.TestFor
import spock.lang.*

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Body)
class BodySpec extends Specification {

    def "should give the correct displacement vector"() {
        given: "A body in a spacial coordinate"
        Body b = new Body(
                x: 5, y: 10, z:27
        )

        when: "the displacement vector is calculated"
        Vector v = b.getDisplacementVector()

        then: "the vector should be correct"
        assert v.x == 5
        assert v.y == 10
        assert v.z == 27

        assert v.magnitude.round(3) == 29.223
    }

    def "should give the correct velocity vector"() {
        given: "A body in a spacial coordinate"
        Body b = new Body(
                vx: 5, vy: 10, vz:27
        )

        when: "the displacement vector is calculated"
        Vector v = b.getVelocityVector()

        then: "the vector should be correct"
        assert v.x == 5
        assert v.y == 10
        assert v.z == 27

        assert v.magnitude.round(3) == 29.223
    }
}
