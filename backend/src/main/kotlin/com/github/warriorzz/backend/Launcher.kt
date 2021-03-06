package com.github.warriorzz.backend

import com.github.warriorzz.backend.core.Database
import com.github.warriorzz.backend.discord.DiscordBot
import com.github.warriorzz.backend.server.startServer
import mu.KotlinLogging

suspend fun main() {
    KotlinLogging.logger {}.info("Starting service...")
    Database()
    startServer()
    DiscordBot()
}
