package ca.gabrielcastro.openotp.model

import android.net.Uri
import ca.gabrielcastro.openotp.ext.base32Decoded
import java.util.*


private const val SCHEME_OTP: String = "otpauth"
private const val TYPE_TOTP: String = "totp"

/**
 * Parses a TOTP URI into a TOTP object.
 */
fun totpFrom(uri: String): Totp? {
    val uriObj = Uri.parse(uri)

    if (SCHEME_OTP != uriObj.scheme) {
        return null
    }
    if (TYPE_TOTP != uriObj.host) {
        return null
    }
    val path = uriObj.pathSegments
    if (path.size < 1) {
        return null
    }

    val label = path[0]
    val secret = uriObj.getQueryParameter("secret")
    val issuer: String? = uriObj.getQueryParameter("issuer")

    val algorithm = (uriObj.getQueryParameter("algorithm") ?: Algorithms.SHA1).toUpperCase(Locale.CANADA)
    if (algorithm != Algorithms.SHA1 && algorithm != Algorithms.SHA256 && algorithm != Algorithms.SHA512) {
        return null
    }
    val digits = uriObj.getQueryParameter("digits") ?: "6"
    val period = uriObj.getQueryParameter("period") ?: "30"

    val digitsInt: Int
    val periodInt: Int

    try {
        digitsInt = Integer.parseInt(digits)
    } catch (e: NumberFormatException) {
        return null
    }

    try {
        periodInt = Integer.parseInt(period)
    } catch (e: NumberFormatException) {
        return null
    }

    val secretBytes = secret.base32Decoded()

    val userLabel: String?
    val userIssuer: String?
    if (issuer != null && issuer.length > 0) {
        userLabel = label
        userIssuer = issuer
    } else {
        val splitted = label.split(':')
        if (splitted.size == 1) {
            userLabel = label
            userIssuer = null
        } else {
            userIssuer = splitted[0]
            userLabel = splitted.subList(1, splitted.lastIndex + 1).joinToString(":")
        }
    }

    return Totp(
            issuer = issuer ?: "",
            userIssuer = userIssuer ?: issuer ?: "",

            accountName = label ?: "",
            userAccountName = userLabel ?: label ?: "",

            algorithm = algorithm,
            digits = digitsInt,
            period = periodInt,
            secret = secretBytes
    )
}
