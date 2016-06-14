package ca.gabrielcastro.openotp.model

import android.support.annotation.StringDef
import java.util.*

/**
 * Defines all the data required to represent one Totp
 */
data class Totp(
        /**
         * Uniquely identifies this inside the app.
         */
        val uuid: String = UUID.randomUUID().toString(),

        /**
         * Where the account is from. ie. Google
         */
        val issuer: String = "",
        /**
         * Same as #issuer, but user editable.
         */
        val userIssuer: String = issuer,

        /**
         * The name of this account. ie. example@gmail.com
         */
        val accountName: String = "",
        /**
         * Same as #accountName but user editable.
         */
        val userAccountName: String = accountName,

        /**
         * What algorithm to use when generating tokens
         */
        @Algorithm
        val algorithm: String = Algorithms.SHA1,

        val secret: ByteArray,

        /**
         * Number of digits in each token.
         */
        val digit: Int = 6,
        /**
         * How often the token refreshes. In seconds
         */
        val period: Int = 30
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other?.javaClass != javaClass) {
            return false
        }
        other as Totp

        return this.uuid == other.uuid &&
                this.issuer == other.issuer &&
                this.userIssuer == other.userIssuer &&
                this.accountName == other.accountName &&
                this.userAccountName == other.userAccountName &&
                this.algorithm == other.algorithm &&
                this.digit == other.digit &&
                this.period == other.period &&
                Arrays.equals(this.secret, other.secret)
    }

}


@StringDef(Algorithms.SHA1, Algorithms.SHA256, Algorithms.SHA512)
annotation class Algorithm;


class Algorithms private constructor() {
    companion object {
        const val SHA1 = "SHA1"
        const val SHA256 = "SHA256"
        const val SHA512 = "SHA512"
    }
    init {
        throw AssertionError("No Instances.")
    }
}
