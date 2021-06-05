package com.github.warriorzz.dynamicbio

import com.github.warriorzz.dynamicbio.db.Database
import com.github.warriorzz.dynamicbio.utils.BiographyProvider

suspend fun main() {
    Database()
    BiographyProvider()
    DynamicBio()
}
