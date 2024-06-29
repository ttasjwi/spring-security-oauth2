package com.ttasjwi.oauth2

import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

class MacTest {

    companion object {

        private const val SECRET_KEY = "cbf41347bb1978f6f32087b2cf01e351"

        fun hmacTest(data: String) {
            hmacBase64(SECRET_KEY, data, "HmacMD5")
            hmacBase64(SECRET_KEY, data, "HmacSHA256")
        }

        private fun hmacBase64(secret: String, data: String, algorithm: String) {
            val secretKeySpec = SecretKeySpec(secret.toByteArray(Charsets.UTF_8), algorithm)
            val mac = Mac.getInstance(algorithm)

            // MAC 사양 초기화 : 비밀키, 알고리즘
            mac.init(secretKeySpec)

            // 원본값을 비밀키를 이용하여 해싱
            val hash = mac.doFinal(data.toByteArray())

            val base64EncodedHash = Base64.getEncoder().encodeToString(hash)

            println("${algorithm}: $base64EncodedHash")
        }
    }
}
