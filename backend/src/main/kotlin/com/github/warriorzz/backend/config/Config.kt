package com.github.warriorzz.backend.config

import dev.schlaubi.envconf.environment

object Config {
    val DATABASE_NAME by environment
    val DATABASE_CONNECTION_STRING by environment
    val DISCORD_TOKEN by environment
}
