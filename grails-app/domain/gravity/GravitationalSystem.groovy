package gravity
import grails.rest.*

@Resource(formats=['json', 'xml'])
class GravitationalSystem {

    String name
    String timeUnit
    String distanceUnit
    Integer timestepAmount
    double G

    static hasMany = [timestep: Timestep, body: Body]

    static constraints = {
    }

    static mapping = {
        id generator:'sequence', params:[sequence:'seq_gravitational_system_id']
    }
}