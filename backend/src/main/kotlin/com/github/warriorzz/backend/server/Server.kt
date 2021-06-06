package com.github.warriorzz.backend.server

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*

fun startServer() = embeddedServer(CIO) {
    install(Routing)

    routing {
        get("/api/v1/") {
            val userId = call.parameters["userId"]?.toLong() ?: call.respond(HttpStatusCode.BadRequest)
            val guildId = call.parameters["guildId"]?.toLong() ?: call.respond(HttpStatusCode.BadRequest)

        }
    }
}