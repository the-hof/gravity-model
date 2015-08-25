package gravity
import grails.rest.*

@Resource(formats=['json', 'xml'])
class Event {

    String Name
    Body systemSource
    Body eventCelebrant
    Double source_x
    Double source_y
    Double source_z
    Double source_mass
    Double source_radius
    Double celebrant_x
    Double celebrant_y
    Double celebrant_z
    Double celebrant_mass
    Double celebrant_radius

    static constraints = {
    }

    static mapping = {
        id generator:'sequence', params:[sequence:'seq_event_id']
    }
}
