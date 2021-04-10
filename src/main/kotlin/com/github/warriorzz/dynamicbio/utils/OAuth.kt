package com.github.warriorzz.dynamicbio.utils

import com.github.warriorzz.dynamicbio.config.Config
import io.ktor.http.*
import org.jetbrains.annotations.TestOnly
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import java.time.Instant
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import kotlin.collections.HashMap

object OAuth {

    private val parameters = HashMap<String, String>()
    private var method: HttpMethod = HttpMethod.Post
    private var url = ""
    private var signingKey =
        "${Config.CONSUMER_ACCESS_SECRET.percentEncode()}&${Config.OAUTH_ACCESS_SECRET.percentEncode()}"

    operator fun invoke() {
        parameters["oauth_version"] = "1.0"
        parameters["oauth_signature_method"] = "HMAC-SHA1"
        parameters["oauth_consumer_key"] = Config.CONSUMER_ACCESS_TOKEN
        parameters["oauth_token"] = Config.OAUTH_ACCESS_TOKEN
    }

    fun withParameters(parameters: Map<String, String>): OAuth {
        parameters.forEach { (key, value) ->
            this.parameters[key] = value
        }
        return this
    }

    fun withMethod(method: HttpMethod): OAuth {
        this.method = method
        return this
    }

    fun withUrl(url: String): OAuth {
        this.url = url
        return this
    }

    fun header(): String {
        parameters["oauth_timestamp"] = Instant.now().epochSecond.toString()
        parameters["oauth_nonce"] = randomString()
        parameters["oauth_signature"] = getSignature() ?: return ""
        return "OAuth " + parameters.map {
            it.key.percentEncode() + "=" + it.value.percentEncode()
        }.sortedWith { first, second ->
            first.compareTo(second)
        }.reduce { acc, string ->
            "$acc, $string"
        }
    }

    fun getSignature(): String? {
        val parameterString = parameters.map {
            it.key.percentEncode() + "=" + it.value.percentEncode()
        }.sortedWith { first, second ->
            first.compareTo(second)
        }.reduce { acc, string ->
            "$acc&$string"
        }
        val baseString = "${method.value}&${url.percentEncode()}&${parameterString.percentEncode()}"

        return generateSignature(signingKey, baseString)
    }

    private fun generateSignature(secret: String, message: String): String? {
        return try {
            val bytes: ByteArray = secret.toByteArray(Charsets.UTF_8)
            val mac = Mac.getInstance("HmacSHA1")
            mac.init(SecretKeySpec(bytes, "HmacSHA1"))
            val result = mac.doFinal(message.toByteArray(Charsets.UTF_8))
            Base64.getEncoder().encodeToString(result)
        } catch (exception: InvalidKeyException) {
            throw RuntimeException(exception)
        } catch (exception: NoSuchAlgorithmException) {
            throw RuntimeException(exception)
        }
    }

    @TestOnly
    fun withSigningKey(key: String) {
        signingKey = key
    }
}
