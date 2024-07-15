package com.ttasjwi.resourceserver.controller

import com.ttasjwi.resourceserver.controller.dto.OpaqueDto
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ApiController {

    @GetMapping("/introspect")
    fun introspectToken(authentication: BearerTokenAuthentication, @AuthenticationPrincipal principal: OAuth2AuthenticatedPrincipal): OpaqueDto {
        val tokenAttributes = authentication.tokenAttributes as Map<String, *>
        val active = tokenAttributes["active"] as Boolean

        return OpaqueDto(active, authentication, principal)
    }
}
