package com.ttasjwi.oauth2

import com.ttasjwi.oauth2.support.logging.getLogger
import org.springframework.security.core.Authentication
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.security.oauth2.core.oidc.user.OidcUser
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class IndexController {

    companion object {
        private val log = getLogger(IndexController::class.java)
    }

    @GetMapping("/")
    fun index(): String {
        return "index"
    }

    @GetMapping("user")
    fun user(authentication: Authentication): OAuth2User {
        val token = authentication as OAuth2AuthenticationToken
        val user = token.principal
        return user
    }

    @GetMapping("/oauth2User")
    fun oauth2User(@AuthenticationPrincipal user: OAuth2User): OAuth2User {
        log.info { "OAuth2User = $user" }
        return user
    }

    @GetMapping("/oidcUser")
    fun oidcUser(@AuthenticationPrincipal user: OidcUser): OidcUser {
        log.info { "OidcUser = $user" }
        return user
    }
}
