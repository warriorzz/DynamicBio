package com.github.warriorzz.dynamicbio.model

import com.github.warriorzz.dynamicbio.utils.BiographieProvider
import kotlinx.serialization.Serializable

@Serializable
data class Biographie(
    val description: String,
    val url: String = BiographieProvider.lastBiographie.url,
    val location: String = BiographieProvider.lastBiographie.location,
)
