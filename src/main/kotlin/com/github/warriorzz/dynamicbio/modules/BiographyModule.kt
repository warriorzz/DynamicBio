package com.github.warriorzz.dynamicbio.modules

interface BiographyModule : Comparable<BiographyModule> {

    suspend fun getModuleString() : String

    fun isEnabled() : Boolean

    fun getPriority() : Int

    override fun compareTo(other: BiographyModule) = getPriority() - other.getPriority()
}