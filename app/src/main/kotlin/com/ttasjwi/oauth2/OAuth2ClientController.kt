package com.ttasjwi.oauth2

import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class OAuth2ClientController(
    private val oauth2AuthorizedClientRepository: OAuth2AuthorizedClientRepository
) {

    @GetMapping("/")
    fun index(httpServletRequest: HttpServletRequest): OAuth2AuthenticationResult {
        // oauth2Client() API는 oauth2AuthorizationCodeAuthenticationToken 만 가져옴
        val clientRegistrationId = "keycloak"
        val beforeAuthentication = SecurityContextHolder.getContextHolderStrategy().context.authentication

        println("beforeAuthentication = $beforeAuthentication")

        val oauth2AuthorizedClient = oauth2AuthorizedClientRepository.loadAuthorizedClient<OAuth2AuthorizedClient>(clientRegistrationId, beforeAuthentication, httpServletRequest)
        val clientRegistration = oauth2AuthorizedClient.clientRegistration

        // 사용자 정보를 가져오려면 accessToken 을 사용해서 서비스제공자로부터 가져와야함
        val oauth2UserService = DefaultOAuth2UserService()
        val oauth2User = oauth2UserService.loadUser(OAuth2UserRequest(clientRegistration, oauth2AuthorizedClient.accessToken))
        val authentication = OAuth2AuthenticationToken(oauth2User, setOf(SimpleGrantedAuthority("ROLE_USER")), clientRegistration.clientId)

        SecurityContextHolder.getContextHolderStrategy().context.authentication = authentication

        return OAuth2AuthenticationResult(
            username = authentication.name,
            name = authentication.principal.attributes["name"] as String,
            roles = authentication.authorities.map { it.authority },
            clientId = clientRegistration.clientId,
            accessToken = oauth2AuthorizedClient.accessToken.tokenValue,
            refreshToken = oauth2AuthorizedClient.refreshToken!!.tokenValue
        )
    }

}
