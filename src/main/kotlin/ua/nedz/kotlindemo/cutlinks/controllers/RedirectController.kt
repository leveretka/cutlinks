package ua.nedz.kotlindemo.cutlinks.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import ua.nedz.kotlindemo.cutlinks.services.KeyMapperService
import javax.servlet.http.HttpServletResponse

@Controller
@RequestMapping("/{key}")
class RedirectController {

    @Autowired
    lateinit var service: KeyMapperService

    @RequestMapping()
    fun redirect(@PathVariable("key") key: String, response: HttpServletResponse) {
        val getResult = service.getLink(key)

        when(getResult) {
            is KeyMapperService.Get.Link ->
                response.apply {
                        setHeader(HEADER_NAME, getResult.link)
                        status = 302
                }

            is KeyMapperService.Get.NotFound -> response.status = 404
        }
    }

    companion object {
        private val HEADER_NAME = "Location"
    }

}
