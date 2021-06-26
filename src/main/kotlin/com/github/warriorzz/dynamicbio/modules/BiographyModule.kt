package com.github.warriorzz.dynamicbio.modules

interface BiographyModule {

    suspend fun getModuleString() : String

    fun isEnabled() : Boolean

}