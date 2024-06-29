package com.ttasjwi.oauth2

import com.ttasjwi.oauth2.SignatureTest.Companion.signatureTest
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class Main

fun main(args: Array<String>) {
//    messageDigestTest("Spring Security")
    signatureTest("Spring Security")
//    runApplication<Main>(*args)
}
