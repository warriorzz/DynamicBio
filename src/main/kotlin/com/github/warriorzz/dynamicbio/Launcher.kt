package com.github.warriorzz.dynamicbio

import com.github.warriorzz.dynamicbio.db.Database
import com.github.warriorzz.dynamicbio.utils.BiographieProvider
import com.github.warriorzz.dynamicbio.utils.OAuth

suspend fun main() {
    OAuth.invoke()
    Database.invoke()
    BiographieProvider.invoke()
    DynamicBio()
}
