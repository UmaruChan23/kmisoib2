package encrypt

import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey


fun keygen(algorithm: String): SecretKey {
    return KeyGenerator.getInstance(algorithm).generateKey()
}

fun encrypt(cipher: Cipher, key: SecretKey, text: ByteArray): ByteArray {
    cipher.init(Cipher.ENCRYPT_MODE, key)
    return cipher.doFinal(text)
}

fun decrypt(cipher: Cipher, key: SecretKey, code: ByteArray): ByteArray {
    cipher.init(Cipher.DECRYPT_MODE, key)
    return cipher.doFinal(code)
}