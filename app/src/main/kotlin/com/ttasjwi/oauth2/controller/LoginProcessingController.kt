package com.ttasjwi.oauth2.controller

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class LoginProcessingController(
    private val oauth2AuthorizedClientManager: OAuth2AuthorizedClientManager,
) {

    private val oauth2UserService = DefaultOAuth2UserService()
    private val authorityMapper = SimpleAuthorityMapper()

    init {
        authorityMapper.setPrefix("SYSTEM_")
    }

    @GetMapping("/oauth2Login")
    fun oauth2Login(request: HttpServletRequest, response: HttpServletResponse, model: Model): String {
        // 익명사용자
        val authentication = SecurityContextHolder.getContextHolderStrategy().context.authentication

        // 자격 증명 발급
        val oauth2AuthorizeRequest = OAuth2AuthorizeRequest
            .withClientRegistrationId("keycloak")
            .principal(authentication)
            .attribute(HttpServletRequest::class.java.name, request)
            .attribute(HttpServletResponse::class.java.name, response)
            .build()

        val oauth2AuthorizedClient = oauth2AuthorizedClientManager.authorize(oauth2AuthorizeRequest)

        if (oauth2AuthorizedClient != null) {
            val clientRegistration = oauth2AuthorizedClient.clientRegistration
            val accessToken = oauth2AuthorizedClient.accessToken
            val oauth2UserRequest = OAuth2UserRequest(clientRegistration, accessToken)
            val oauth2User = oauth2UserService.loadUser(oauth2UserRequest)

            val grantedAuthorities = authorityMapper.mapAuthorities(oauth2User.authorities)

            val oauth2AuthenticationToken = OAuth2AuthenticationToken(oauth2User, grantedAuthorities, clientRegistration.registrationId)

            SecurityContextHolder.getContextHolderStrategy().context.authentication = oauth2AuthenticationToken
            model.addAttribute("oauth2AuthenticationToken", oauth2AuthenticationToken)
        }
        return "home"
    }
}
