package com.github.warriorzz.dynamicbio.utils

import com.github.warriorzz.dynamicbio.db.Database
import com.github.warriorzz.dynamicbio.model.Biographie

object BiographieProvider {

    private val list = arrayListOf<Biographie>()
    var lastBiographie = Biographie("written by Leon", "github.com/warriorzz/dynamicbio", "Twitter")

    suspend operator fun invoke() {
        Database.biographieCollection.find().consumeEach {
            list.add(it)
        }
    }

    fun getNextBio(): Biographie {
        lastBiographie = list.random()
        return lastBiographie
    }
}
