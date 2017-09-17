package ua.nedz.kotlindemo.cutlinks.services

import org.springframework.stereotype.Component
import java.lang.StringBuilder

@Component
class DefaultKeyConverterService : KeyConverterService {

    private val chars = "qazwsxedcrfvtgbyhnujmikolpQAZWSXEDCRFVTGBYHNUJMIKOLP1234567890-_".toCharArray()

    val charToLong = (0 until chars.size)
            .map { chars[it] to it.toLong() }
            .toMap()

    override fun idToKey(id: Long): String {
        var n = id
        val builder = StringBuilder()
        while (n != 0L) {
            builder.append(chars[(n % chars.size).toInt()])
            n /= chars.size
        }
        return builder.reverse().toString()
    }

    override fun keyToId(key: String) = key
            .map { charToLong[it]!! }
            .fold(0L, { a, b -> a * chars.size + b })
}