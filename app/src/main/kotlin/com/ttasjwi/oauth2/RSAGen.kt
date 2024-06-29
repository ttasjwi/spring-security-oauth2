package com.ttasjwi.oauth2

import java.nio.charset.StandardCharsets
import java.security.*
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import java.util.*
import javax.crypto.Cipher

class RSAGen {

    companion object {

        fun genKeyPair(): KeyPair {
            val generator = KeyPairGenerator.getInstance("RSA")
            generator.initialize(2048, SecureRandom())
            return generator.genKeyPair()
        }

        fun encryptWithPublicKey(plainText: String, publicKey: PublicKey): String {
            val cipher = Cipher.getInstance("RSA")
            cipher.init(Cipher.ENCRYPT_MODE, publicKey)

            val bytePlain = cipher.doFinal(plainText.toByteArray())
            return Base64.getEncoder().encodeToString(bytePlain)
        }

        fun decryptWithPrivateKey(encrypted: String, privateKey: PrivateKey): String {
            val cipher = Cipher.getInstance("RSA")
            cipher.init(Cipher.DECRYPT_MODE, privateKey)

            // Base64 디코딩
            val byteEncrypted = Base64.getDecoder().decode(encrypted.toByteArray())

            // 복호화
            val bytePlain = cipher.doFinal(byteEncrypted)
            return String(bytePlain, StandardCharsets.UTF_8)
        }

        fun getPublicKeyFromKeySpec(base64PublicKey: String?): PublicKey {
            val decodedBase64PubKey = Base64.getDecoder().decode(base64PublicKey)
            return KeyFactory.getInstance("RSA").generatePublic(X509EncodedKeySpec(decodedBase64PubKey))
        }

        fun getPrivateKeyFromKeySpec(base64PrivateKey: String?): PrivateKey {
            val decodedBase64PrivateKey = Base64.getDecoder().decode(base64PrivateKey)

            return KeyFactory.getInstance("RSA").generatePrivate(PKCS8EncodedKeySpec(decodedBase64PrivateKey))
        }

    }
}
