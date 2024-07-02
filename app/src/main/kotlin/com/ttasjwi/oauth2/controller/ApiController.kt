package com.ttasjwi.oauth2.controller

import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ApiController {

    @GetMapping("/")
    fun index(): String = "index"

    @GetMapping("/api/user")
    fun user(authentication: Authentication): Authentication {
        return authentication
    }
}
