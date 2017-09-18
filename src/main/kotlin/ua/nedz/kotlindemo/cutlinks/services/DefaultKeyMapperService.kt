package ua.nedz.kotlindemo.cutlinks.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import ua.nedz.kotlindemo.cutlinks.model.Link
import ua.nedz.kotlindemo.cutlinks.model.repositories.LinkRepository
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong

@Component
class DefaultKeyMapperService : KeyMapperService {

    @Autowired
    lateinit var converter: KeyConverterService

    @Autowired
    lateinit var repository: LinkRepository

    override fun add(link: String) = converter.idToKey(repository.save(Link(link)).id)

    override fun getLink(key: String): KeyMapperService.Get {
        val result = repository.findOne(converter.keyToId(key))
        return if (result.isPresent)
            KeyMapperService.Get.Link(result.get().text)
        else
            KeyMapperService.Get.NotFound(key)

    }
}