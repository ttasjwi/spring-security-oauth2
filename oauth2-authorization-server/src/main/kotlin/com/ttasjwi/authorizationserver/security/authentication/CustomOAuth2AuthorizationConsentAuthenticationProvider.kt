package com.ttasjwi.authorizationserver.security.authentication

import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationCodeRequestAuthenticationToken
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationConsentAuthenticationProvider
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationConsentAuthenticationToken
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository

class CustomOAuth2AuthorizationConsentAuthenticationProvider(
    registeredClientRepository: RegisteredClientRepository,
    authorizationService: OAuth2AuthorizationService,
    authorizationConsentService: OAuth2AuthorizationConsentService
) : AuthenticationProvider {

    private val delegate = OAuth2AuthorizationConsentAuthenticationProvider(
        registeredClientRepository,
        authorizationService,
        authorizationConsentService
    )

    override fun authenticate(authentication: Authentication?): Authentication? {
        val authenticationResult = delegate.authenticate(authentication) as OAuth2AuthorizationCodeRequestAuthenticationToken?

        println("동의후...")
        println("principal = ${authenticationResult?.principal}")
        return authenticationResult
    }

    override fun supports(authentication: Class<*>): Boolean {
        return OAuth2AuthorizationConsentAuthenticationToken::class.java.isAssignableFrom(authentication)
    }
}
