package com.ttasjwi.oauth2.security

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.client.OAuth2AuthorizationSuccessHandler
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository

class CustomOAuth2AuthorizationSuccessHandler(
    private val oauth2AuthorizedClientRepository: OAuth2AuthorizedClientRepository,
) : OAuth2AuthorizationSuccessHandler {


    override fun onAuthorizationSuccess(
        authorizedClient: OAuth2AuthorizedClient?,
        principal: Authentication?,
        attributes: MutableMap<String, Any>?
    ) {
        oauth2AuthorizedClientRepository.saveAuthorizedClient(
            authorizedClient,
            principal,
            attributes!![HttpServletRequest::class.java.name] as HttpServletRequest,
            attributes[HttpServletResponse::class.java.name] as HttpServletResponse,
        )

        println("authorizedClient = $authorizedClient")
        println("principal = $principal")
        println("attributes = $attributes")
    }
}
