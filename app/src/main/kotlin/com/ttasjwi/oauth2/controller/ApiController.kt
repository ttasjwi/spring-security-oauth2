package com.ttasjwi.oauth2.controller

import org.springframework.security.core.Authentication
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ApiController {

    @GetMapping("/")
    fun index(): String = "index"

    @GetMapping("/api/user")
    fun user(authentication: JwtAuthenticationToken, @AuthenticationPrincipal principal: Jwt): Authentication {
        val sub = authentication.tokenAttributes["sub"] as String
        val email = authentication.tokenAttributes["email"] as String?
        val scope = authentication.tokenAttributes["scope"] as String

        val sub2 = principal.getClaim<String>("sub")
        val token = principal.tokenValue


        // 토큰을 사용해 다른 우리 서비스와 통신할 수도 있다.
//        val restTemplate = RestTemplate()
//        val headers = HttpHeaders()
//        headers.add("Authorization", "Bearer $token")
//
//        val requestEntity = RequestEntity<String>(headers, HttpMethod.GET, URI("http://localhost:8082"))
//        val responseEntity = restTemplate.exchange<String>(requestEntity)
//        val body = responseEntity.body

        return authentication
    }
}
