package ca.gabrielcastro.openotp.model

import ca.gabrielcastro.openotp.BuildConfig
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricGradleTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricGradleTestRunner::class)
@Config(
        constants = BuildConfig::class,
        sdk = intArrayOf(17)
)
class TotpUriTests {

    @Test
    fun testUriParse() {
        val valid = "otpauth://totp/Organization%3Amail%40example.org?secret=VISSUAMOQUNAYCNKGL&issuer=OIssuer"
        val totp = totpFrom(valid)

        assertTotp(totp, "OIssuer", "Organization:mail@example.org", "SHA1", 6, 30)
    }

    @Test
    fun testCustomDigits() {
        val valid = "otpauth://totp/Organization%3Amail%40example.org?secret=VISSUAMOQUNAYCNKGL&issuer=OIssuer&digits=8"
        val totp = totpFrom(valid)

        assertTotp(totp, "OIssuer", "Organization:mail@example.org", "SHA1", 8, 30)
    }

    @Test
    fun testCustomAlgorithm() {
        val valid = "otpauth://totp/Organization%3Amail%40example.org?secret=VISSUAMOQUNAYCNKGL&issuer=OIssuer&algorithm=SHA256"
        val totp = totpFrom(valid)

        assertTotp(totp, "OIssuer", "Organization:mail@example.org", "SHA256", 6, 30)
    }

    @Test
    fun testCustomPeriod() {
        val valid = "otpauth://totp/Organization%3Amail%40example.org?secret=VISSUAMOQUNAYCNKGL&issuer=OIssuer&period=60"
        val totp = totpFrom(valid)

        assertTotp(totp, "OIssuer", "Organization:mail@example.org", "SHA1", 6, 60)
    }

    @Test
    fun testBadAlgorithm() {
        val valid = "otpauth://totp/Organization%3Amail%40example.org?secret=VISSUAMOQUNAYCNKGL&issuer=OIssuer&period=60&algorithm=SHA123"
        val totp = totpFrom(valid)
        assertThat(totp)
                .describedAs("with a bag algorithm")
                .isNull()
    }

    private fun assertTotp(totp: Totp?, issuer: String, label: String, algo: String, digits: Int, period: Int) {
        assertThat(totp)
                .isNotNull()
        // smart cast already checked above.
        totp!!

        assertThat(totp)
                .describedAs("totp")
                .isNotNull()

        assertThat(totp.issuer)
                .describedAs("issuer")
                .isEqualTo(issuer)

        assertThat(totp.accountName)
                .describedAs("label")
                .isEqualTo(label)

        assertThat(totp.algorithm)
                .describedAs("algorithm")
                .isEqualTo(algo)

        assertThat(totp.digits)
                .describedAs("digits")
                .isEqualTo(digits)

        assertThat(totp.period)
                .describedAs("period")
                .isEqualTo(period)
    }

}
