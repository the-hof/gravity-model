package gravity


import grails.converters.JSON

class EventController {
    def eventCalculatorService

    def index() {
        def systemId = params.GravitationalSystemId
        def timestep1 = params.timestep1
        def timestep2 = params.timestep2

        def result = [params: params]
        render result as JSON
    }

    def create() {
        def stellarSystem = GravitationalSystem.get(params.GravitationalSystemId)
        def timestep1 = params.int('timestep1')
        def timestep2 = params.int('timestep2')

        def initialTimestep = stellarSystem.timestep.find{ it.number == 0 }
        def lastTimestep = stellarSystem.timestep.find{ it.number == timestep1 }
        def thisTimestep = stellarSystem.timestep.find{ it.number == timestep2 }

        def result = [events: eventCalculatorService.lookForEvents(initialTimestep,
                lastTimestep,
                thisTimestep,
                "Sun", "Earth")]
    }
}
