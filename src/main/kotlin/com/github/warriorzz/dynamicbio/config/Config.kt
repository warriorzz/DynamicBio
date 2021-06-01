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

    val ENVIRONMENT by getEnv(default = Environment.PRODUCTION.name) { Environment.valueOf(it) }

}

enum class Environment {
    PRODUCTION, DEVELOPMENT;
}
