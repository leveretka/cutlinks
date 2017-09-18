package ua.nedz.kotlindemo.cutlinks.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import ua.nedz.kotlindemo.cutlinks.services.KeyMapperService

@RestController
@RequestMapping("/add")
class AddController {

    @Autowired
    lateinit var service: KeyMapperService

    @RequestMapping(method = arrayOf(RequestMethod.POST))
    fun add(@RequestBody request: AddRequest) =
            ResponseEntity.ok(AddResponse(request.link, service.add(request.link)))


    data class AddResponse(val link: String, val key: String)

    data class AddRequest(val link: String)
}

