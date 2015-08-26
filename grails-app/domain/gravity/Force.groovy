package gravity
import grails.rest.*

@Resource(formats=['json', 'xml'])
class Force {

    static belongsTo = [ timestep: Timestep ]

    Body thisBody
    Body causingBody
    String type
    Double magnitude
    Double fx
    Double fy
    Double fz

    static constraints = {
        type nullable: true
    }

    public Vector getForceVector() {
        Vector force = new Vector(
                x: this.fx,
                y: this.fy,
                z: this.fz
        )

        force.setMagnitude()

        return force
    }
}
