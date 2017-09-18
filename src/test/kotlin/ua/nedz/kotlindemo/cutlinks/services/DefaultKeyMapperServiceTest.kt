package ua.nedz.kotlindemo.cutlinks.services

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import ua.nedz.kotlindemo.cutlinks.model.Link
import ua.nedz.kotlindemo.cutlinks.model.repositories.LinkRepository
import ua.nedz.kotlindemo.cutlinks.whenever
import java.util.*

class DefaultKeyMapperServiceTest {

    @InjectMocks
    val service: KeyMapperService = DefaultKeyMapperService()

    @Mock
    lateinit var converter: KeyConverterService

    @Mock
    lateinit var repository: LinkRepository

    private val KEY = "aAbBcCdD"

    private val LINK_A = "http://google.com"
    private val LINK_B = "http://yahoo.com"

    private val KEY_A = "abc"
    private val KEY_B = "cde"
    private val ID_A = 10000000L
    private val ID_B = 10000001L

    private val LINK_OBJ_A = Link(LINK_A, ID_A)
    private val LINK_OBJ_B = Link(LINK_B, ID_B)

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)

        Mockito.`when`(converter.keyToId(KEY_A)).thenReturn(ID_A)
        Mockito.`when`(converter.keyToId(KEY_B)).thenReturn(ID_B)
        Mockito.`when`(converter.idToKey(ID_A)).thenReturn(KEY_A)
        Mockito.`when`(converter.idToKey(ID_B)).thenReturn(KEY_B)

        whenever(repository.findOne(Mockito.anyObject())).thenReturn(Optional.empty())
        whenever(repository.save(Link(LINK_A))).thenReturn(LINK_OBJ_A)
        whenever(repository.save(Link(LINK_B))).thenReturn(LINK_OBJ_B)
        whenever(repository.findOne(ID_A)).thenReturn(Optional.of(LINK_OBJ_A))
        whenever(repository.findOne(ID_B)).thenReturn(Optional.of(LINK_OBJ_B))
    }

    @Test
    fun clientCanAddLinks() {
        val keyA = service.add(LINK_A)
        assertEquals(KeyMapperService.Get.Link(LINK_A), service.getLink(keyA))
        val keyB = service.add(LINK_B)
        assertEquals(KeyMapperService.Get.Link(LINK_B), service.getLink(keyB))
    }

    @Test
    fun clientCannotTakeLinkIfKeyIsNotFound() {
        assertEquals(KeyMapperService.Get.NotFound(KEY), service.getLink(KEY))
    }

}