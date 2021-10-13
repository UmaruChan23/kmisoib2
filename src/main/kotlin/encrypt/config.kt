package encrypt.encrypt

import org.bouncycastle.jce.provider.BouncyCastleProvider
import java.security.Security

fun configure() {
    Security.setProperty("crypto.policy", "unlimited")
    Security.addProvider(BouncyCastleProvider())
}