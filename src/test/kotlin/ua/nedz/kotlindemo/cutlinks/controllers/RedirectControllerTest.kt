package ua.nedz.kotlindemo.cutlinks.controllers

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.header
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import ua.nedz.kotlindemo.cutlinks.CutlinksApplication
import ua.nedz.kotlindemo.cutlinks.services.KeyMapperService

@RunWith(SpringRunner::class)
@SpringBootTest(classes = arrayOf(CutlinksApplication::class))
@WebAppConfiguration
@TestPropertySource(locations = arrayOf("classpath:repositories-test.properties"))
class RedirectControllerTest {

    @Autowired
    lateinit var webApplicationContext: WebApplicationContext

    @Mock
    lateinit var service: KeyMapperService

    @Autowired
    @InjectMocks
    lateinit var controller: RedirectController

    lateinit var mockMvc: MockMvc

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build()

        Mockito.`when`(service.getLink(PATH)).thenReturn(KeyMapperService.Get.Link(HEADER_VALUE))
        Mockito.`when`(service.getLink(BAD_PATH)).thenReturn(KeyMapperService.Get.NotFound(BAD_PATH))
    }

    private val PATH = "aAbBcCdD"
    private val REDIRECT_STATUS = 302
    private val HEADER_NAME = "Location"
    private val HEADER_VALUE = "http://start.spring.io"

    @Test
    fun controllerMustRedirectUsWhenRequestIsSuccessful() {
        mockMvc.perform(MockMvcRequestBuilders.get("/$PATH"))
                .andExpect(status().`is`(REDIRECT_STATUS))
                .andExpect(header().string(HEADER_NAME, HEADER_VALUE))
    }

    private val BAD_PATH = "olololo"
    private val NOT_FOUND = 404

    @Test
    fun controllerMustReturn404IfBadKey() {
        mockMvc.perform(MockMvcRequestBuilders.get("/$BAD_PATH"))
                .andExpect(status().`is`(NOT_FOUND))
    }


}
