package gravity
import grails.rest.*

@Resource(formats=['json', 'xml'])
class Force {

    static belongsTo = [ timestep: Timestep ]

    Body thisBody
    Body causingBody
    Double magnitude
    Double fx
    Double fy
    Double fz

    static constraints = {
    }
}
