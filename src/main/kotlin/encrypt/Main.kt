package encrypt

import encrypt.encrypt.configure
import javax.crypto.Cipher
import javax.crypto.SecretKey


fun main() {
    configure()
    val filename = "C:\\Users\\UmaruChan\\IdeaProjects\\kmisoib2\\src\\main\\resources\\message.txt"
    val algorithm = "DES"
    val cipher = Cipher.getInstance(algorithm, "BC")
    val key: SecretKey = keygen(algorithm)

    encryptFile(cipher, key,filename)
    decryptFile(cipher, key, "file_enc.txt")
}