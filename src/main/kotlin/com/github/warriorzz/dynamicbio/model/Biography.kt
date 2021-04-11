package com.github.warriorzz.dynamicbio.model

import com.github.warriorzz.dynamicbio.utils.BiographyProvider
import kotlinx.serialization.Serializable

@Serializable
data class Biography(
    val description: String,
    val url: String = BiographyProvider.lastBiography.url,
    val location: String = BiographyProvider.lastBiography.location,
)
