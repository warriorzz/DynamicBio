package com.github.warriorzz.backend

import com.github.warriorzz.backend.discord.DiscordBot
import com.github.warriorzz.backend.server.startServer

suspend fun main() {
    startServer()
    DiscordBot()
}