package gravity

import grails.transaction.Transactional

@Transactional
class EventCalculatorService {

    def List<Event> lookForEvents(Timestep initial, Timestep lastTimestep, Timestep thisTimestep,
                                  String source, String celebrant) {
        Body source_initial = initial.body.find { it.name == source }
        Body source_last = lastTimestep.body.find { it.name == source }
        Body source_this = thisTimestep.body.find { it.name == source }
        Body celebrant_initial = initial.body.find { it.name == celebrant }
        Body celebrant_last = lastTimestep.body.find { it.name == celebrant }
        Body celebrant_this = thisTimestep.body.find { it.name == celebrant }
    }
}
