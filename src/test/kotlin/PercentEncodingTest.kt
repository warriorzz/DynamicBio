import com.github.warriorzz.dynamicbio.utils.percentEncode
import org.junit.jupiter.api.Test

object PercentEncodingTest {

    // unnecessary...
    @Test
    fun `Test Percent Encoding`() {
        val value = " !".percentEncode()
        val expected = "%20%21"
        assert(value == expected)
    }
}
