package com.ttasjwi.oauth2

import java.security.MessageDigest
import java.security.SecureRandom

class MessageDigestTest {

    companion object {

        // 솔트
        private lateinit var salt: ByteArray

        // 해시 결과
        private lateinit var digest: ByteArray

        fun messageDigestTest(message: String) {
            createMD5(message)
            validateMD5(message)
        }

        private fun createMD5(message: String) {
            val random = SecureRandom()

            // 암호화를 위한 salt
            val salt = ByteArray(10)
            random.nextBytes(salt)

            this.salt = salt

            val messageDigest = MessageDigest.getInstance("MD5")
            messageDigest.update(salt)
            messageDigest.update(message.toByteArray(charset("UTF-8")))

            // 해시 결과
            this.digest = messageDigest.digest()
        }

        private fun validateMD5(message: String) {
            // 원본값
            val messageDigest = MessageDigest.getInstance("MD5")

//            val wrongSalt = salt.copyOf(9)
//            messageDigest.update(wrongSalt)
            messageDigest.update(salt)
            messageDigest.update(message.toByteArray(charset("UTF-8")))

            val digest = messageDigest.digest()

            if (this.digest.contentEquals(digest)) {
                println("message matches.")
            }
            else {
                println("message does not matches.")
            }
        }
    }
}
