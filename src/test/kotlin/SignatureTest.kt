import com.github.warriorzz.dynamicbio.config.Config
import com.github.warriorzz.dynamicbio.utils.OAuth
import com.github.warriorzz.dynamicbio.utils.percentEncode
import io.ktor.http.*
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

object SignatureTest {

    @Test
    fun `Test Signature`() {
        val expected = "NfqsB0u3ssrm%2Fv2Ly6UEuzP4SYg%3D"

        OAuth()
        OAuth.withMethod(HttpMethod.Post).withParameters(
            mapOf(
                "oauth_timestamp" to (1617827407.toString()),
                "oauth_nonce" to ("b8aKGHfzD9oHq9kx4hK4BdRWRMHM1eRr"),
                "description" to "written by Leon",
                "url" to "sbin.tk",
                "oauth_consumer_key" to "NtPbenizqX8MZCHqgsOjzHSW5",
                "oauth_token" to "1261729604907270145-KezcRcq76ZIHoYqYAWnz2afRDxERG2"
            )
        ).withUrl("https://api.twitter.com/1.1/account/update_profile.json")
            .withSigningKey("dTeXRH4UAAXAQaysCUYBu47eGgS6T5Rb9iiqOnRwR7S1hvOnYy&2icFRw2WyREv254X6GV6LJoc7qj5wa6wjfJdM0hlSQzNI")
        assertEquals(expected, OAuth.getSignature()?.percentEncode())
    }
}
