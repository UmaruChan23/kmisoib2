package encrypt

import java.io.BufferedWriter
import java.io.File
import java.nio.charset.Charset
import javax.crypto.Cipher
import javax.crypto.SecretKey

fun encryptFile(cipher: Cipher, key: SecretKey, filename: String) {
    val file = File(filename)
    val dist = File("file_enc.txt")
    delIfNotNull(dist)
    dist.createNewFile()
    file.forEachLine {
        val sz = it.toByteArray(Charset.forName("CP1251"))
        val out = encrypt(cipher, key, sz).toHex()
        file.inputStream().use {
            dist.appendText("$out\n")
        }
    }
}

fun decryptFile(cipher: Cipher, key: SecretKey, filename: String) {
    val file = File(filename)
    val dist = File("file_dec.txt")
    delIfNotNull(dist)
    dist.createNewFile()
    file.forEachLine {
        val sz = it.decodeHex()
        val out = decrypt(cipher, key, sz)
        file.inputStream().use {
            dist.appendText("${out.toString(Charset.forName("CP1251"))}\n")
        }
    }
}

@ExperimentalUnsignedTypes
fun ByteArray.toHex(): String = asUByteArray().joinToString("") { it.toString(radix = 16).padStart(2, '0') }

fun String.decodeHex(): ByteArray {
    check(length % 2 == 0) { "Must have an even length" }

    return chunked(2)
        .map { it.toInt(16).toByte() }
        .toByteArray()
}

fun BufferedWriter.writeLn(line: String) {
    this.append(line)
    this.newLine()
}

fun delIfNotNull(file: File) {
    if(file.exists()) file.delete()
}