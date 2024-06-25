package com.ttasjwi.oauth2

import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ApiController {

    @GetMapping("/")
    fun authentication(authentication: Authentication): Authentication {
        return authentication
    }
}
