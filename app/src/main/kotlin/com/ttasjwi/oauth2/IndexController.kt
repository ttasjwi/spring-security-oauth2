package com.ttasjwi.oauth2

import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.OAuth2AccessToken
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames
import org.springframework.security.oauth2.core.oidc.OidcIdToken
import org.springframework.security.oauth2.core.oidc.user.OidcUser
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.time.Instant

@RestController
class IndexController(
    private val clientRegistrationRepository: ClientRegistrationRepository,
) {

    @GetMapping("/")
    fun index(): String {
        return "index"
    }


    /**
     * OAuth 2.0 방식
     */
    @GetMapping("/user")
    fun oauth2User(accessToken: String): OAuth2User {
        val clientRegistration = clientRegistrationRepository.findByRegistrationId("keycloak")!!

        val oauth2AccessToken = OAuth2AccessToken(
            OAuth2AccessToken.TokenType.BEARER,
            accessToken,
            Instant.now(),
            Instant.MAX
        )
        val request = OAuth2UserRequest(clientRegistration, oauth2AccessToken)
        val oauth2UserService = DefaultOAuth2UserService()

        val oauth2User = oauth2UserService.loadUser(request)
        return oauth2User
    }

    /**
     * OpenID Connect 방식
     */
    @GetMapping("/oidc")
    fun oidcUser(accessToken: String, idToken: String): OidcUser {
        val clientRegistration = clientRegistrationRepository.findByRegistrationId("keycloak")!!

        val oauth2AccessToken = OAuth2AccessToken(
            OAuth2AccessToken.TokenType.BEARER,
            accessToken,
            Instant.now(),
            Instant.MAX
        )

        val idTokenClaims = mapOf(
            IdTokenClaimNames.ISS to "http://localhost:8080/realms/oauth2",
            IdTokenClaimNames.SUB to "5800c6e2-203d-4f0e-8343-bbb948f16807",
            "preferred_username" to "user",
        )
        val oidcIdToken = OidcIdToken(idToken, Instant.now(), Instant.MAX, idTokenClaims)
        val request = OidcUserRequest(clientRegistration, oauth2AccessToken, oidcIdToken)
        val oidcUserService = OidcUserService()

        val oidcUser = oidcUserService.loadUser(request)
        return oidcUser
    }
}
