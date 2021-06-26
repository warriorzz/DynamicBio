package com.github.warriorzz.dynamicbio.config

import dev.schlaubi.envconf.environment
import dev.schlaubi.envconf.getEnv

object Config {

    val OAUTH_ACCESS_TOKEN by environment
    val OAUTH_ACCESS_SECRET by environment
    val CONSUMER_ACCESS_TOKEN by environment
    val CONSUMER_ACCESS_SECRET by environment

    val DATABASE_CONNECTION_STRING by environment
    val DATABASE_NAME by environment

    val INTERVAL by getEnv(default = 15) { it.toInt() }
    val ENVIRONMENT by getEnv(default = Environment.PRODUCTION.name) { Environment.valueOf(it) }

    val ENABLE_DISCORD by getEnv(default = false) { it.toBoolean() }
    val DISCORD_API_URL by getEnv(default = "")
    val DISCORD_GUILD_ID by getEnv(default = "")
    val DISCORD_USER_ID by getEnv(default = "")
}

enum class Environment {
    PRODUCTION, DEVELOPMENT;
}
