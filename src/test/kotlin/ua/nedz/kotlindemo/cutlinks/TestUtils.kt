package ua.nedz.kotlindemo.cutlinks

import org.mockito.Mockito

fun <T> whenever(call: T) = Mockito.`when`(call)