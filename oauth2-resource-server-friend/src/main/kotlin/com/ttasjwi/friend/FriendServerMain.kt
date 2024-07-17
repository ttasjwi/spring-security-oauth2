package com.ttasjwi.friend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FriendServerMain

fun main(args: Array<String>) {
    runApplication<FriendServerMain>(*args)
}
