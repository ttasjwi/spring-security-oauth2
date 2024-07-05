package com.ttasjwi.oauth2.controller

import com.ttasjwi.oauth2.controller.dto.OpaqueTokenResponse
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ApiController {

    @GetMapping("/")
    fun user(authentication: BearerTokenAuthentication, @AuthenticationPrincipal principal: OAuth2AuthenticatedPrincipal): OpaqueTokenResponse {
        val attributes =  authentication.tokenAttributes as Map<String, *>
        val active = attributes["active"] as Boolean

        return OpaqueTokenResponse(active, principal, authentication)
    }
}
