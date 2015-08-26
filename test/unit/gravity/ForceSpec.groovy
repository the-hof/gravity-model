package gravity

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Force)
class ForceSpec extends Specification {

    def "should give the correct force vector"() {
        given: "A body in a spacial coordinate"
        Force b = new Force(
                fx: 5, fy: 10, fz:27
        )

        when: "the displacement vector is calculated"
        Vector v = b.getForceVector()

        then: "the vector should be correct"
        assert v.x == 5
        assert v.y == 10
        assert v.z == 27

        assert v.magnitude.round(3) == 29.223
    }
}
