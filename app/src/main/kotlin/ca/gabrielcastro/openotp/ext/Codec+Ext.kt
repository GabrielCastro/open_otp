package ca.gabrielcastro.openotp.ext

import org.apache.commons.codec.binary.Base32

/**
 * Encode this byte[] into a base32 string
 */
fun ByteArray.base32Encoded(): String {
    return Base32().encodeAsString(this)
}

/**
 * Decodes this base32 encoded string into a byte[]
 */
fun String.base32Decoded(): ByteArray {
    return Base32().decode(this)
}
