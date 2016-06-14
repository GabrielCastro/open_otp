package ca.gabrielcastro.openotp.model

import nl.jqno.equalsverifier.EqualsVerifier
import org.junit.Test

class TotpTest {

    @Test
    fun testEquals() {
        EqualsVerifier.forClass(Totp::class.java).verify()
    }

}
