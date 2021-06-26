package com.github.warriorzz.dynamicbio.utils

import com.github.warriorzz.dynamicbio.db.Database
import com.github.warriorzz.dynamicbio.model.Biography
import com.github.warriorzz.dynamicbio.modules.impl.DiscordModule

object BiographyProvider {

    private val list = mutableSetOf(
        Biography("written by Leon", "github.com/warriorzz/dynamicbio", "Twitter"),
    )
    lateinit var lastBiography: Biography

    private val moduleList = mutableSetOf(DiscordModule)
    private const val SEPARATOR = " - "

    suspend operator fun invoke() {
        Database.biographyCollection.find().consumeEach {
            list.add(it)
            lastBiography = it
        }
    }

    suspend fun getNextBio(): Triple<String, String, String> {
        lastBiography = list.let { list ->
            if (list.size == 1) list.first()
            else list.filter { entry -> entry != lastBiography }.random()
        }
        val descriptionString = lastBiography.description + SEPARATOR + moduleList.filter {
            it.isEnabled()
        }.sorted().map {
            it.getModuleString()
        }.reduce { acc, s ->
            if (s == "") acc
            else "$acc$SEPARATOR$s"
        }
        return Triple(descriptionString, lastBiography.url, lastBiography.location)
    }
}
