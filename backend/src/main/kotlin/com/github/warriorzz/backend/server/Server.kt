package com.github.warriorzz.backend.server

import com.github.warriorzz.backend.discord.DiscordBot
import dev.kord.common.entity.Snowflake
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import mu.KotlinLogging

suspend fun startServer() = embeddedServer(CIO) {
    val logger = KotlinLogging.logger {}

    install(Routing) {
        get("/api/v1") {
            val userId = call.parameters["userId"]?.toLong()
            val guildId = call.parameters["guildId"]?.toLong()
            if (userId == null || guildId == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@get
            }
            logger.info { "Received request for userId = $userId and guildId = $guildId! ID: ${call.callId}" }
            call.respondText {
                DiscordBot.getCurrentActivity(guildId = Snowflake(guildId), userId = Snowflake(userId))
            }
        }
    }
}.start()