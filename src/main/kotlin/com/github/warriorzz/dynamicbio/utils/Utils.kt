package com.github.warriorzz.dynamicbio.utils

import java.net.URLEncoder
import kotlin.random.Random

private val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

fun randomString(): String {
    return (1..Random.nextInt(48, 122))
        .map { Random.nextInt(0, charPool.size) }
        .map(charPool::get)
        .joinToString("")
}

fun String.percentEncode(): String {
    return URLEncoder.encode(this, Charsets.UTF_8).replace("+", "%20")
}
