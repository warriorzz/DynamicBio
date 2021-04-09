package com.github.warriorzz.dynamicbio.model

import kotlinx.serialization.Serializable

@Serializable
data class TwitterResponse(
    val url: String,
    val location: String,
    val name: String
    // more stuff should be added
)
