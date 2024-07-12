package com.ttasjwi.authorizationserver.security.authentication

import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationCodeRequestAuthenticationProvider
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationCodeRequestAuthenticationToken
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationConsentAuthenticationToken
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository

class CustomOAuth2AuthorizationCodeRequestAuthenticationProvider(
    registeredClientRepository: RegisteredClientRepository,
    authorizationService: OAuth2AuthorizationService,
    authorizationConsentService: OAuth2AuthorizationConsentService
): AuthenticationProvider {

    private val delegate = OAuth2AuthorizationCodeRequestAuthenticationProvider(
        registeredClientRepository, authorizationService, authorizationConsentService)

    override fun authenticate(authentication: Authentication): Authentication? {
        val authenticationResult = delegate.authenticate(authentication)

        if (authenticationResult is OAuth2AuthorizationCodeRequestAuthenticationToken && !authentication.isAuthenticated) {
            println("동의전 - 미인증 상태입니다.")
        }
        if (authenticationResult is OAuth2AuthorizationConsentAuthenticationToken) {
            println("동의전 - 동의가 필요합니다.")
        }
        println("principal = ${authenticationResult?.principal}")
        return authenticationResult
    }

    override fun supports(authentication: Class<*>): Boolean {
        return OAuth2AuthorizationCodeRequestAuthenticationToken::class.java.isAssignableFrom(authentication)
    }

}
