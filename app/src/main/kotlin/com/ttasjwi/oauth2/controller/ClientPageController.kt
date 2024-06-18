package com.ttasjwi.oauth2.controller

import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class ClientPageController(
    private val authorizedClientRepository: OAuth2AuthorizedClientRepository,
    private val authorizedClientService: OAuth2AuthorizedClientService
) {

    @GetMapping("/client")
    fun client(request: HttpServletRequest, model: Model): String {
        // oauth2Client API 를 사용할 경우, SecurityContext에는 익명사용자만 남아있다.
        val authentication = SecurityContextHolder.getContextHolderStrategy().context.authentication

        println("authentication = $authentication")

        val clientRegistrationId = "keycloak"

        // 대신 Oauth2AuthorizedClient 가 AuthorizedClientRepository 에 있으므로 이를 사용해 인증에 사용할 수 있다
        val oauth2AuthorizedClient = authorizedClientRepository.loadAuthorizedClient<OAuth2AuthorizedClient>(
            clientRegistrationId,
            authentication,
            request
        )
        val oauth2AuthorizedClient1 = authorizedClientService.loadAuthorizedClient<OAuth2AuthorizedClient>(
            clientRegistrationId,
            authentication.name
        )
        println("oauth2AuthorizedClient = $oauth2AuthorizedClient")
        println("oauth2AuthorizedClient1 = $oauth2AuthorizedClient1")


        // Oauth2AuthorizedClient 를 통해 여러가지 정보 조회 가능(ClientRegistration, accessToken, refreshToken, principalName)
        val accessToken = oauth2AuthorizedClient.accessToken
        val refreshToken = oauth2AuthorizedClient.refreshToken!!
        val clientRegistration = oauth2AuthorizedClient.clientRegistration
        val principalName = oauth2AuthorizedClient.principalName

        println("accessToken = $accessToken")
        println("refreshToken = $refreshToken")
        println("clientRegistration = $clientRegistration")
        println("principalName = $principalName")

        // Oauth2UserService 를 통해 사용자 정보(OAuth2) 조회
        val oauth2UserService = DefaultOAuth2UserService()
        val oauth2User = oauth2UserService.loadUser(OAuth2UserRequest(clientRegistration, accessToken))


        // 인증 생성(Authentication) 및 저장
        val authenticationToken =
            OAuth2AuthenticationToken(oauth2User, setOf(SimpleGrantedAuthority("ROLE_USER")), clientRegistrationId)
        SecurityContextHolder.getContextHolderStrategy().context.authentication = authenticationToken
        println("authenticationToken = $authenticationToken")


        model.addAttribute("accessToken", accessToken.tokenValue)
        model.addAttribute("refreshToken", refreshToken.tokenValue)
        model.addAttribute("principalName", oauth2User.name)
        model.addAttribute("clientName", oauth2AuthorizedClient.clientRegistration.clientName)
        return "client"
    }
}
