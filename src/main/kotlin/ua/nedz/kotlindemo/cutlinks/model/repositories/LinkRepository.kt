package ua.nedz.kotlindemo.cutlinks.model.repositories

import org.springframework.data.repository.Repository
import ua.nedz.kotlindemo.cutlinks.model.Link
import java.util.*

interface LinkRepository : Repository<Link, Long> {
    fun findOne(id: Long?): Optional<Link>

    fun save(link: Link): Link

    fun findAll(): List<Link>
}