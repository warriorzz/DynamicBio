package com.github.warriorzz.dynamicbio.utils

import com.github.warriorzz.dynamicbio.db.Database
import com.github.warriorzz.dynamicbio.model.Biography
import com.github.warriorzz.dynamicbio.modules.impl.DiscordModule

object BiographyProvider {

    private val list = mutableListOf(
        Biography("written by Leon", "github.com/warriorzz/dynamicbio", "Twitter")
    )
    var lastBiography = list.random()

    private val moduleList = mutableListOf(DiscordModule)
    private const val SEPARATOR = " - "

    suspend operator fun invoke() {
        Database.biographyCollection.find().consumeEach {
            list.add(it)
        }
    }

    suspend fun getNextBio(): Biography {
        lastBiography = list.filter { it != lastBiography }.random()
        lastBiography.description = lastBiography.description + moduleList.filter {
            it.isEnabled()
        }.sorted().map {
            it.getModuleString()
        }.reduce { acc, s -> "$acc$SEPARATOR$s" }
        return lastBiography
    }
}
