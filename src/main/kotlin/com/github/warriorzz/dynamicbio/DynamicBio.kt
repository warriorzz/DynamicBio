package com.github.warriorzz.dynamicbio

import com.github.warriorzz.dynamicbio.config.Config
import com.github.warriorzz.dynamicbio.model.TwitterResponse
import com.github.warriorzz.dynamicbio.utils.BiographyProvider
import com.github.warriorzz.dynamicbio.utils.OAuth
import dev.inmo.krontab.doInfinity
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.http.*
import mu.KotlinLogging

object DynamicBio {

    private var initialized = false
    private val logger = KotlinLogging.logger {}

    internal val httpClient = HttpClient(OkHttp) {
        install(JsonFeature) {
            val json = kotlinx.serialization.json.Json {
                ignoreUnknownKeys = true
            }
            serializer = KotlinxSerializer(json)
        }
    }

    suspend operator fun invoke() {
        require(!initialized) { "Cannot initialize DynamicBio twice!" }
        doInfinity("0 /${Config.INTERVAL} * * *") {
            updateBio()
        }
    }

    private suspend fun updateBio() {
        val (description, url, location) = BiographyProvider.getNextBio()

        logger.info { "Updating bio..." }
        httpClient.post<TwitterResponse>("https://api.twitter.com/1.1/account/update_profile.json") {
            parameter("description", description)
            parameter("url", url)
            parameter("location", location)
            headers.clear()
            header(
                "Authorization",
                OAuth.withUrl("https://api.twitter.com/1.1/account/update_profile.json").withMethod(HttpMethod.Post)
                    .withParameters(
                        mapOf(
                            "description" to description,
                            "url" to url,
                            "location" to location
                        )
                    ).authenticationHeaders()
            )
        }
        // maybe do some stuff with response
    }
}
