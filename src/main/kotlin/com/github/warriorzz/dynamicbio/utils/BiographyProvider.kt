package com.github.warriorzz.dynamicbio.utils

import com.github.warriorzz.dynamicbio.db.Database
import com.github.warriorzz.dynamicbio.model.Biography
import com.github.warriorzz.dynamicbio.modules.impl.DiscordModule

object BiographyProvider {

    private val list = mutableSetOf(
        Biography("written by Leon", "github.com/warriorzz/dynamicbio", "Twitter"),
    )
    var lastBiography = list.random()
    var initialized = false

    private val moduleList = mutableSetOf(DiscordModule)
    private const val SEPARATOR = " - "

    suspend operator fun invoke() {
        Database.biographyCollection.find().consumeEach {
            list.add(it)
            lastBiography = it
        }
        initialized = true
    }

    suspend fun getNextBio(): Triple<String, String, String> {
        lastBiography = list.let { list ->
            if (list.size == 1) list.first()
            else list.filter { entry -> entry != lastBiography }.random()
        }
        val moduleString = moduleList.filter {
            it.isEnabled()
        }.let {
            return@let if (it.isEmpty()) ""
            else it.sorted().map { module ->
                module.getModuleString()
            }.reduce { acc, s ->
                if (s == "") acc
                else "$acc$SEPARATOR$s"
            }
        }
        val descriptionString = lastBiography.description + if (moduleString.length != 0) SEPARATOR + moduleString else ""
        return Triple(descriptionString, lastBiography.url, lastBiography.location)
    }
}
