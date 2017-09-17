package ua.nedz.kotlindemo.cutlinks.services

interface KeyConverterService {

    fun idToKey(id: Long): String

    fun keyToId(key: String): Long
}