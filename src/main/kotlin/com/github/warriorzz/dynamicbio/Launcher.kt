package com.github.warriorzz.dynamicbio

import com.github.warriorzz.dynamicbio.db.Database
import com.github.warriorzz.dynamicbio.utils.BiographyProvider
import com.github.warriorzz.dynamicbio.utils.OAuth

suspend fun main() {
    OAuth()
    Database()
    BiographyProvider()
    DynamicBio()
}
