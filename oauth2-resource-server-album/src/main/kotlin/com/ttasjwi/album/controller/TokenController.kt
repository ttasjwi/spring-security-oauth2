package com.ttasjwi.album.controller

import org.springframework.security.oauth2.core.OAuth2Error
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TokenController {

    @GetMapping("/tokenExpire")
    fun tokenExpire(): Map<String, Any> {
        val result: MutableMap<String, Any> = HashMap()
        result["error"] = OAuth2Error("invalid token", "token is expired", null)

        return result
    }
}
