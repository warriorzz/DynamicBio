package com.github.warriorzz.dynamicbio.modules.impl

import com.github.warriorzz.dynamicbio.DynamicBio
import com.github.warriorzz.dynamicbio.config.Config
import com.github.warriorzz.dynamicbio.modules.BiographyModule
import io.ktor.client.request.*

class DiscordModule : BiographyModule {
    override suspend fun getModuleString(): String {
        return DynamicBio.httpClient.get(Config.DISCORD_API_URL) {
            parameter("userId", Config.DISCORD_USER_ID)
            parameter("guildId", Config.DISCORD_GUILD_ID)
        }
    }

    override fun isEnabled(): Boolean = Config.ENABLE_DISCORD
}