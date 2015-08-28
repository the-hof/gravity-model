import grails.rest.render.xml.*
import grails.rest.render.json.*

import gravity.Body
import gravity.Timestep
import gravity.GravitationalSystem


// Place your Spring DSL code here
beans = {
    bodyJSONRenderer(JsonRenderer, Body) {
        excludes = ['class', 'timestep', 'gravitationalSystem']
    }
    bodyXmlRenderer(XmlRenderer, Body) {
        excludes = ['class', 'timestep', 'gravitationalSystem']
    }

    gravitationalSystemJSONRenderer(JsonRenderer, GravitationalSystem) {
        excludes = ['class']
    }
    gravitationalSystemXmlRenderer(XmlRenderer, GravitationalSystem) {
        excludes = ['class']
    }

    timestepJSONRenderer(JsonRenderer, Timestep) {
        excludes = ['class', 'gravitationalSystem']
    }
    timestepXmlRenderer(XmlRenderer, Timestep) {
        excludes = ['class', 'gravitationalSystem']
    }
}
