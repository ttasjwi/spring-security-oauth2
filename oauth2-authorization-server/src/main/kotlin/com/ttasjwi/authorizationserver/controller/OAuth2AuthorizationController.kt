package com.ttasjwi.authorizationserver.controller

import org.springframework.security.oauth2.server.authorization.OAuth2Authorization
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class OAuth2AuthorizationController(
    private val oauth2AuthorizationService: OAuth2AuthorizationService
) {

    @GetMapping("/authorization")
    fun oauth2Authorization(token: String): OAuth2Authorization {
        return oauth2AuthorizationService.findByToken(token, OAuth2TokenType.ACCESS_TOKEN)!!
    }
}
