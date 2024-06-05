package com.ttasjwi.oauth2

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class IndexController {

    @GetMapping("/")
    fun index(): String {
        return "index"
    }
}