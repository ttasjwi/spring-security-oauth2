package com.ttasjwi.authorizationserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AuthorizationServerMain

fun main(args: Array<String>) {
    runApplication<AuthorizationServerMain>(*args)
}
