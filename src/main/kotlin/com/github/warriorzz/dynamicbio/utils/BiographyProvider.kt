package com.github.warriorzz.dynamicbio.utils

import com.github.warriorzz.dynamicbio.db.Database
import com.github.warriorzz.dynamicbio.model.Biography

object BiographyProvider {

    private val list = mutableListOf(
        Biography("written by Leon", "github.com/warriorzz/dynamicbio", "Twitter")
    )
    var lastBiography = list.random()
    var initialized = false

    suspend operator fun invoke() {
        Database.biographyCollection.find().consumeEach {
            list.add(it)
        }
        initialized = true
    }

    fun getNextBio(): Biography {
        if (initialized) {
            lastBiography = list.filter { it != lastBiography }.random()
        }
        return lastBiography
    }
}
