package gravity
import grails.converters.JSON

class BodyController {

    def index() {
        def systemId = params.GravitationalSystemId
        def bodyList = Body.findAll {
            gravitationalSystem == GravitationalSystem.get(systemId)
        }

        def result = [body: bodyList]
        render result as JSON
    }

    def show() {
        def systemId = params.GravitationalSystemId
        def body = Body.get(params.id)

        if (body && (body.gravitationalSystem != GravitationalSystem.get(systemId))) {
            body = null
        }

        def result = [body: body]
        render result as JSON
    }
}
