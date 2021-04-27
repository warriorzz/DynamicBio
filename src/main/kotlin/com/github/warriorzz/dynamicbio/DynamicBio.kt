package com.github.warriorzz.dynamicbio

import com.github.warriorzz.dynamicbio.model.TwitterResponse
import com.github.warriorzz.dynamicbio.utils.BiographyProvider
import com.github.warriorzz.dynamicbio.utils.OAuth
import dev.inmo.krontab.builder.buildSchedule
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
    val logger = KotlinLogging.logger {}

    private val httpClient = HttpClient(OkHttp) {
        install(JsonFeature) {
            val json = kotlinx.serialization.json.Json {
                ignoreUnknownKeys = true
            }
            serializer = KotlinxSerializer(json)
        }
    }

    suspend operator fun invoke() {
        require(!initialized) { "Cannot initialize DynamicBio twice!" }
        buildSchedule {
            hours {
                0 every 1
            }
        }.doInfinity {
            updateBio()
        }
    }

    private suspend fun updateBio() {
        val bio = BiographyProvider.getNextBio()

        httpClient.post<TwitterResponse>("https://api.twitter.com/1.1/account/update_profile.json") {
            parameter("description", bio.description)
            parameter("url", bio.url)
            parameter("location", bio.location)
            header(
                "Authorization",
                OAuth.withUrl("https://api.twitter.com/1.1/account/update_profile.json").withMethod(HttpMethod.Post)
                    .withParameters(
                        mapOf(
                            "description" to bio.description,
                            "url" to bio.url,
                            "location" to bio.location
                        )
                    ).authenticationHeaders()
            )
        }
        logger.error { "Bio successfully updated!" }

        // maybe do some stuff with response
    }
}
