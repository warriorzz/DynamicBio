package com.github.warriorzz.dynamicbio.utils

import com.github.warriorzz.dynamicbio.db.Database
import com.github.warriorzz.dynamicbio.model.Biography

object BiographyProvider {

    private val list = arrayListOf<Biography>()
    var lastBiography = Biography("written by Leon", "github.com/warriorzz/dynamicbio", "Twitter")

    suspend operator fun invoke() {
        Database.biographieCollection.find().consumeEach {
            list.add(it)
        }
    }

    fun getNextBio(): Biography {
        lastBiography = list.random()
        return lastBiography
    }
}
