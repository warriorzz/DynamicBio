package com.github.warriorzz.backend.discord

import com.github.warriorzz.backend.config.Config
import dev.kord.common.entity.ActivityType
import dev.kord.common.entity.Snowflake
import dev.kord.core.Kord
import dev.kord.gateway.Intent
import dev.kord.gateway.PrivilegedIntent
import mu.KotlinLogging

object DiscordBot {
    private val logger = KotlinLogging.logger("Discord")
    private var initialized = false
    private lateinit var kord: Kord

    @OptIn(PrivilegedIntent::class)
    suspend operator fun invoke() {
        require(!initialized) {
            logger.error { "Cannot initialize twice!" }
        }
        initialized = true
        kord = Kord(Config.DISCORD_TOKEN) {
            intents += Intent.GuildPresences
        }
        logger.info { "Started discord bot!" }
        kord.login()
    }

    suspend fun getCurrentActivity(guildId: Snowflake, userId: Snowflake) : String {
        val presence = kord.getGuild(guildId)?.getMember(userId)?.getPresence() ?: return ""
        return presence.activities.filter {
            it.name != "null" && it.applicationId != null
        }.map {
            it.type.getActivityString() + it.name
        }.first()
    }
}

private fun ActivityType.getActivityString() = when (this) {
    ActivityType.Listening -> "listening to "
    ActivityType.Streaming -> "streaming "
    ActivityType.Watching -> "watching "
    ActivityType.Game -> "playing "
    else -> ""
}
