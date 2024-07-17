package com.ttasjwi.album

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AlbumServerMain

fun main(args: Array<String>) {
    runApplication<AlbumServerMain>(*args)
}
