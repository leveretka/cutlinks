package ua.nedz.kotlindemo.cutlinks.services

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

class DefaultKeyConverterServiceTest {

    private val service = DefaultKeyConverterService()

    @Test
    fun givenIdMustBeConvertableBothWays() {
        val rand = Random()
        for (i in 0..1000L) {
            val initialId = Math.abs(rand.nextLong())
            val key = service.idToKey(initialId)
            val id = service.keyToId(key)
            assertEquals(initialId, id)

        }
    }
}