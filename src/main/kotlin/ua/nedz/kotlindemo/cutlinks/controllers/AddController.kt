package ua.nedz.kotlindemo.cutlinks.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import ua.nedz.kotlindemo.cutlinks.services.KeyMapperService

@Controller
class AddController {

    @Value("\${cutlinks.prefix}")
    private lateinit var prefix: String

    @Autowired
    lateinit var service: KeyMapperService

    @RequestMapping(path = arrayOf("add"), method = arrayOf(RequestMethod.POST))
    @ResponseBody
    fun addRest(@RequestBody request: AddRequest) = ResponseEntity.ok(add(request.link))

    @RequestMapping(path = arrayOf("addhtml"), method = arrayOf(RequestMethod.POST))
    fun addHtml(model: Model, @RequestParam(value = "link", required = true) link: String): String {
        val result = add(link)
        with(model) {
            addAttribute("link", result.link)
            addAttribute("key", prefix + result.key)
        }
        return "result"
    }

    private fun add(link: String) = AddResponse(link, service.add(link))

    data class AddResponse(val link: String, val key: String)

    data class AddRequest(val link: String)
}

