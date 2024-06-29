package com.ttasjwi.oauth2

import java.security.KeyPairGenerator
import java.security.Signature
import java.security.SignatureException

class SignatureTest {

    companion object {

        fun signatureTest(message: String) {
            val keyPairGenerator = KeyPairGenerator.getInstance("RSA")
            keyPairGenerator.initialize(2048)
            val keyPair = keyPairGenerator.genKeyPair()

            val signature = Signature.getInstance("SHA256WithRSA")

            val data = message.toByteArray(charset("UTF-8"))

            // 서명 생성(비밀키 사용)
            signature.initSign(keyPair.private)
            signature.update(data)
            val sign = signature.sign()

            // 검증 (공개키 사용)
            signature.initVerify(keyPair.public)
            signature.update(data)

            var verified = false
            try {
                verified = signature.verify(sign)
            } catch (e: SignatureException) {
                println("전자서명 검증과정에서 오류발생")
                e.printStackTrace()
            }
            println(if (verified) "전자서명 검증 성공" else "전자서명 검증 실패")
        }
    }
}
