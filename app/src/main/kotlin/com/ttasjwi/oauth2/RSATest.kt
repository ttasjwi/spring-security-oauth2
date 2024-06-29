package com.ttasjwi.oauth2

import java.util.*

class RSATest {

    companion object {

        fun rsaTest(message: String) {
            // 키페어 생성
            val keyPair = RSAGen.genKeyPair()
            val publicKey = keyPair.public
            val privateKey = keyPair.private

            println("original: $message")

            println("--------------------------------------------------------------------")

            // 원본값 암호화 및 복호화
            val encrypted = RSAGen.encryptWithPublicKey(message, publicKey)
            val decrypted = RSAGen.decryptWithPrivateKey(encrypted, privateKey)

            println("decrypted: $decrypted")

            println("-------------------------------------------------------------------")

            // 키 스펙 전환하기
            val bytePublicKey = publicKey.encoded
            val base64PublicKey = Base64.getEncoder().encodeToString(bytePublicKey)
            val bytePrivateKey = privateKey.encoded
            val base64PrivateKey = Base64.getEncoder().encodeToString(bytePrivateKey)

            // X.509 표준형식
            val x509PublicKey = RSAGen.getPublicKeyFromKeySpec(base64PublicKey)

            val encrypted2 = RSAGen.encryptWithPublicKey(message, x509PublicKey)
            val decrypted2 = RSAGen.decryptWithPrivateKey(encrypted2, privateKey)
            println("decrypted2: $decrypted2")

            println("-------------------------------------------------------------------")

            // PKCS8 표준형식
            val pKCS8PrivateKey = RSAGen.getPrivateKeyFromKeySpec(base64PrivateKey)
            val decrypted3 = RSAGen.decryptWithPrivateKey(encrypted2, pKCS8PrivateKey)
            println("decrypted3: $decrypted3")
        }
    }
}
