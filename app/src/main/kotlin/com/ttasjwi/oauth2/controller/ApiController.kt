package com.ttasjwi.oauth2.controller

import com.ttasjwi.oauth2.model.users.AuthUser
import org.springframework.security.core.Authentication
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ApiController {


    @GetMapping("/api/userInfo")
    fun user(authentication: Authentication, @AuthenticationPrincipal user: AuthUser): Authentication? {
        println("authentication = $authentication, authUser = $user")
        return authentication
    }
}
