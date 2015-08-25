class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/system"(resources:"GravitationalSystem") {
            "/timestep"(resources:"Timestep")
            "/body"(resources:"Body")
            "/event"(resources:"Event")
        }
        "/"(view:"/index")
        "500"(view:'/error')
    }
}
