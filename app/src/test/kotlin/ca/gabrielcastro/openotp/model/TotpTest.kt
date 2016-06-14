package ca.gabrielcastro.openotp.model

import nl.jqno.equalsverifier.EqualsVerifier
import org.junit.Test
import kotlin.test.assertEquals

class TotpTest {

    @Test
    fun testEquals() {
        EqualsVerifier.forClass(Totp::class.java).verify()
    }


    private val SECRET16 = "12345678901234567890"
    private val SECRET32 = "12345678901234567890123456789012"
    private val SECRET64 = "1234567890123456789012345678901234567890123456789012345678901234"

    private data class TestCase constructor(
            val time: Long,
            val result: Long,
            val algorithm: String,
            val secret: String
    )

    private val cases = arrayOf(
            TestCase(0 * 30 + 1L, 84755224, "SHA1", SECRET16),
            TestCase(1 * 30 + 1L, 94287082, "SHA1", SECRET16),
            TestCase(2 * 30 + 1L, 37359152, "SHA1", SECRET16),
            TestCase(3 * 30 + 1L, 26969429, "SHA1", SECRET16),
            TestCase(4 * 30 + 1L, 40338314, "SHA1", SECRET16),
            TestCase(5 * 30 + 1L, 68254676, "SHA1", SECRET16),
            TestCase(6 * 30 + 1L, 18287922, "SHA1", SECRET16),
            TestCase(7 * 30 + 1L, 82162583, "SHA1", SECRET16),
            TestCase(8 * 30 + 1L, 73399871, "SHA1", SECRET16),
            TestCase(9 * 30 + 1L, 45520489, "SHA1", SECRET16),
            TestCase(2 * 30 - 1L, 94287082L, "SHA1", SECRET16),
            TestCase(2 * 30 - 1L, 46119246L, "SHA256", SECRET32),
            TestCase(2 * 30 - 1L, 90693936L, "SHA512", SECRET64),
            TestCase(1111111109L, 7081804L, "SHA1", SECRET16),
            TestCase(1111111109L, 68084774L, "SHA256", SECRET32),
            TestCase(1111111109L, 25091201L, "SHA512", SECRET64),
            TestCase(1111111111L, 14050471L, "SHA1", SECRET16),
            TestCase(1111111111L, 67062674L, "SHA256", SECRET32),
            TestCase(1111111111L, 99943326L, "SHA512", SECRET64),
            TestCase(1234567890L, 89005924L, "SHA1", SECRET16),
            TestCase(1234567890L, 91819424L, "SHA256", SECRET32),
            TestCase(1234567890L, 93441116L, "SHA512", SECRET64),
            TestCase(2000000000L, 69279037L, "SHA1", SECRET16),
            TestCase(2000000000L, 90698825L, "SHA256", SECRET32),
            TestCase(2000000000L, 38618901L, "SHA512", SECRET64),
            TestCase(20000000000L, 65353130L, "SHA1", SECRET16),
            TestCase(20000000000L, 77737706L, "SHA256", SECRET32),
            TestCase(20000000000L, 47863826L, "SHA512", SECRET64)
    )

    @Test
    fun testCases() {
        var i = 0;
        for (testCase in cases) {
            val totp = Totp(
                    secret = testCase.secret.toByteArray(charset("ASCII")),
                    algorithm = testCase.algorithm,
                    digits = 8
            )
            val result = totp.calculateCode(testCase.time * 1000)
            assertEquals(testCase.result, result, "Totp value for ${i++}: $testCase")
        }
    }

}
