package encrypt

import java.io.BufferedWriter
import java.io.File
import java.nio.charset.Charset
import javax.crypto.Cipher
import javax.crypto.SecretKey

fun encryptFile(cipher: Cipher, key: SecretKey, filename: String) {
    println("encrypt")
    val file = File(filename)
    val dist = File("file_enc.txt")
    delIfNotNull(dist)
    dist.createNewFile()
    val buff = ByteArray(cipher.blockSize)
    file.inputStream().buffered().use {
        while(true) {
            val sz = it.read(buff)
            if (sz <= 0) break
            dist.appendBytes(encrypt(cipher, key, buff))
        }
    }
}

fun decryptFile(cipher: Cipher, key: SecretKey, filename: String) {
    println("decrypt")
    val file = File(filename)
    val dist = File("file_dec.txt")
    delIfNotNull(dist)
    dist.createNewFile()
    val buff = ByteArray(cipher.blockSize * 2)
    file.inputStream().buffered().use {
        while(true) {
            val sz = it.read(buff)
            if (sz <= 0) break
            dist.appendBytes(decrypt(cipher, key, buff))
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