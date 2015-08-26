package gravity
import grails.rest.*

@Resource(formats=['json', 'xml'])
class Timestep {
    static belongsTo = [gravitationalSystem: GravitationalSystem]
    static hasMany = [body: Body]
    Integer number;

    static constraints = {
    }

    static mapping = {
        id generator:'sequence', params:[sequence:'seq_timestep_id']
    }

    Body getBodyByName(String name) {
        return this.body.find({ it.name == name })
    }
}
