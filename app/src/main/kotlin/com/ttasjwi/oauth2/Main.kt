package com.ttasjwi.oauth2

import com.ttasjwi.oauth2.MacTest.Companion.hmacTest
import com.ttasjwi.oauth2.MessageDigestTest.Companion.messageDigestTest
import com.ttasjwi.oauth2.RSATest.Companion.rsaTest
import com.ttasjwi.oauth2.SignatureTest.Companion.signatureTest
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class Main

fun main(args: Array<String>) {
//    messageDigestTest("Spring Security")
//    signatureTest("Spring Security")
//    hmacTest("Spring Security")
    rsaTest("Spring Security")
//    runApplication<Main>(*args)
}
