package gravity
import grails.rest.*

@Resource(formats=['json', 'xml'])
class Body {
    static belongsTo = [gravitationalSystem: GravitationalSystem, timestep: Timestep]
    String name
    Double x
    Double y
    Double z
    Double mass
    Double radius
    Double vx
    Double vy
    Double vz

    static constraints = {
        timestep nullable: true
    }

    static mapping = {
        id generator:'sequence', params:[sequence:'seq_body_id']
    }
}
