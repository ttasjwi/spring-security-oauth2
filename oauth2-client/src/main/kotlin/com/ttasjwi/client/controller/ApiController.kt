package com.ttasjwi.client.controller

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.security.oauth2.client.registration.ClientRegistration
import org.springframework.security.oauth2.core.AuthorizationGrantType
import org.springframework.security.oauth2.core.OAuth2AccessToken
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestOperations

@RestController
class ApiController(
    private val restOperations: RestOperations,
    private val oAuth2AuthorizedClientService: OAuth2AuthorizedClientService,
    private val oAuth2AuthorizedClientManager: OAuth2AuthorizedClientManager,
){

    @GetMapping("/token")
    fun token(@RegisteredOAuth2AuthorizedClient("springoauth2") client: OAuth2AuthorizedClient): OAuth2AccessToken {
        return client.accessToken
    }

    @GetMapping("/tokenExpire")
    fun expireToken(authentication: OAuth2AuthenticationToken): Map<String, Any> {
        val client = oAuth2AuthorizedClientService.loadAuthorizedClient<OAuth2AuthorizedClient>(authentication.authorizedClientRegistrationId, authentication.name)

        val header = HttpHeaders()

        header.add("Authorization", "Bearer " + client.accessToken.tokenValue)
        val entity: HttpEntity<*> = HttpEntity<Any>(header)
        val url = "http://localhost:8082/tokenExpire"
        val response = restOperations.exchange(
            url,
            HttpMethod.GET,
            entity,
            object : ParameterizedTypeReference<Map<String, Any>>() {})

        println("토큰 만료!")
        return response.body!!
    }

    @GetMapping("/newAccessToken")
    fun newAccessToken(authentication: OAuth2AuthenticationToken, request: HttpServletRequest, response: HttpServletResponse): OAuth2AccessToken {
        var client = oAuth2AuthorizedClientService.loadAuthorizedClient<OAuth2AuthorizedClient>(authentication.authorizedClientRegistrationId, authentication.name)

        if (client != null && client.refreshToken != null) {
            println("토큰 재발급 시작")
            val clientRegistration = ClientRegistration
                .withClientRegistration(client.clientRegistration)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .build()

            oAuth2AuthorizedClientService.removeAuthorizedClient(authentication.authorizedClientRegistrationId, authentication.name)

            client = OAuth2AuthorizedClient(clientRegistration, authentication.name, client.accessToken, client.refreshToken)

            val oAuth2AuthorizeRequest =
                OAuth2AuthorizeRequest.withAuthorizedClient(client)
                    .principal(authentication)
                    .attribute(HttpServletRequest::class.java.name, request)
                    .attribute(HttpServletResponse::class.java.name, response)
                    .build()
            client = oAuth2AuthorizedClientManager.authorize(oAuth2AuthorizeRequest)
            oAuth2AuthorizedClientService.saveAuthorizedClient(client, authentication)
        }
        return client.accessToken
    }

}
