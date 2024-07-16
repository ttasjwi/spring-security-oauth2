package com.ttasjwi.resourceserver.controller

import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ApiController {

    @GetMapping("/jwks")
    fun introspectToken(authentication: Authentication): Authentication {
        return authentication
    }
}
