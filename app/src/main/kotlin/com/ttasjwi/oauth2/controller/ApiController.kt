package com.ttasjwi.oauth2.controller

import org.springframework.security.core.Authentication
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.oidc.user.OidcUser
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ApiController {


    @GetMapping("/api/user")
    fun user(authentication: Authentication, @AuthenticationPrincipal oAuth2User: OAuth2User): Authentication? {
        println("authentication = $authentication, oAuth2User = $oAuth2User")
        return authentication
    }

    @GetMapping("/api/oidc") // 요청시 scope 에 openid 가 포함되어야 oidcUser 가 생성된다
    fun oidc(authentication: Authentication, @AuthenticationPrincipal oidcUser: OidcUser): Authentication {
        println("authentication = $authentication, oidcUser = $oidcUser")
        return authentication
    }
}
