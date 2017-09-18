package ua.nedz.kotlindemo.cutlinks.controllers

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.hamcrest.Matchers.containsString
import org.hamcrest.Matchers.equalTo
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import ua.nedz.kotlindemo.cutlinks.CutlinksApplication
import ua.nedz.kotlindemo.cutlinks.services.KeyMapperService
import ua.nedz.kotlindemo.cutlinks.whenever

@RunWith(SpringRunner::class)
@SpringBootTest(classes = arrayOf(CutlinksApplication::class))
@WebAppConfiguration
@TestPropertySource(locations = arrayOf("classpath:repositories-test.properties"))
class AddControllerTest {
    @Autowired
    lateinit var webApplicationContext: WebApplicationContext

    @Mock
    lateinit var service: KeyMapperService

    @Autowired
    @InjectMocks
    lateinit var controller: AddController

    lateinit var mockMvc: MockMvc

    private val KEY = "key"

    private val LINK = "link"

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build()

        whenever(service.add(LINK)).thenReturn(KEY)
    }

    @Test
    fun whenUserAddLinkHeTakesAKey() {
        mockMvc.perform(post("/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jacksonObjectMapper().writeValueAsString(AddController.AddRequest(LINK))))
        .andExpect(jsonPath("$.key", equalTo(KEY)))
        .andExpect(jsonPath("$.link", equalTo(LINK)))
    }

    @Test
    fun whenUserAddLinkByFormHeTakesWebPage() {
        mockMvc.perform(post("/addhtml")
                .param("link", LINK)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
        .andExpect(status().isOk)
        .andExpect(content().string(containsString(KEY)))
        .andExpect(content().string(containsString(LINK)))
    }
}