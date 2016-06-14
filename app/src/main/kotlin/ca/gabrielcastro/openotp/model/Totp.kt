package ca.gabrielcastro.openotp.model

import android.support.annotation.StringDef
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

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
        val digits: Int = 6,
        /**
         * How often the token refreshes. In seconds
         */
        val period: Int = 30
) {

    fun calculateCode(millsSinceEpoch: Long = System.currentTimeMillis()) : Long {
        val interval = millsSinceEpoch / 1000 / period

        val mac = Mac.getInstance("HMAC" + algorithm)
        val key = SecretKeySpec(secret, "RAW")
        val challenge = ByteBuffer.allocate(8).order(ByteOrder.BIG_ENDIAN).putLong(interval).array()
        mac.init(key)
        val result = mac.doFinal(challenge)!!

        val offset = result[result.size - 1].toInt() and 0xF

        val longResult = ByteBuffer.wrap(result).order(ByteOrder.BIG_ENDIAN).getInt(offset) and 0x7FFFFFFF

        return longResult % Math.pow(10.toDouble(), digits.toDouble()).toLong()
    }

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
                this.digits == other.digits &&
                this.period == other.period &&
                Arrays.equals(this.secret, other.secret)
    }

    override fun hashCode(): Int{
        var result = uuid.hashCode()
        result = 31 * result + issuer.hashCode()
        result = 31 * result + userIssuer.hashCode()
        result = 31 * result + accountName.hashCode()
        result = 31 * result + userAccountName.hashCode()
        result = 31 * result + algorithm.hashCode()
        result = 31 * result + Arrays.hashCode(secret)
        result = 31 * result + digits
        result = 31 * result + period
        return result
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
