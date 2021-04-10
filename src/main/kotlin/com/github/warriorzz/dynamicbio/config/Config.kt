package com.github.warriorzz.dynamicbio.config

import io.github.cdimascio.dotenv.dotenv

object Config {

    val OAUTH_ACCESS_TOKEN = byEnv("OAUTH_ACCESS_TOKEN")
    val OAUTH_ACCESS_SECRET = byEnv("OAUTH_ACCESS_SECRET")
    val CONSUMER_ACCESS_TOKEN = byEnv("CONSUMER_ACCESS_TOKEN")
    val CONSUMER_ACCESS_SECRET = byEnv("CONSUMER_ACCESS_SECRET")
    val DATABASE_HOST = byEnv("DATABASE_HOST")
    val DATABASE_NAME = byEnv("DATABASE_NAME")
    val DATABASE_USER = byEnv("DATABASE_USER")
    val DATABASE_PASSWORD = byEnv("DATABASE_PASSWORD")
}

private val dotenv = dotenv {
    ignoreIfMalformed = true
    ignoreIfMissing = true
}

fun byEnv(property: String, default: String = ""): String {
    if (dotenv[property] != null) {
        return dotenv[property]
    }
    return default
}
