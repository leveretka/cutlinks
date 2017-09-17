package ua.nedz.kotlindemo.cutlinks.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong

@Component
class DefaultKeyMapperService : KeyMapperService {

    @Autowired
    lateinit var converter: KeyConverterService

    val sequence = AtomicLong(10000000L)

    private val map: MutableMap<Long, String> = ConcurrentHashMap()

    override fun add(link: String): String {
        val id = sequence.getAndIncrement()
        val key = converter.idToKey(id)
        map.put(id, link)
        return key
    }

    override fun getLink(key: String): KeyMapperService.Get {
        val id: Long = converter.keyToId(key)
        return if (map[id] != null)
            KeyMapperService.Get.Link(map[id]!!)
        else
            KeyMapperService.Get.NotFound(key)
    }
}