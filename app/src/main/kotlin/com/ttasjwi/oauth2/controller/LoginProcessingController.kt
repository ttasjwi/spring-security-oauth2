package com.ttasjwi.oauth2.controller

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.client.OAuth2AuthorizationSuccessHandler
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class LoginProcessingController(
    private val oauth2AuthorizationSuccessHandler: OAuth2AuthorizationSuccessHandler
) {
    private val oauth2UserService = DefaultOAuth2UserService()
    private val authorityMapper = SimpleAuthorityMapper()

    companion object {
        private fun createAttributes(
            servletRequest: HttpServletRequest,
            servletResponse: HttpServletResponse
        ): Map<String, Any> {
            return mapOf(
                HttpServletRequest::class.java.name to servletRequest,
                HttpServletResponse::class.java.name to servletResponse
            )
        }
    }

    @GetMapping("/oauth2Login")
    fun oauth2Login(
        model: Model,
        @RegisteredOAuth2AuthorizedClient("keycloak") oauth2AuthorizedClient: OAuth2AuthorizedClient?,
        servletRequest: HttpServletRequest,
        servletResponse: HttpServletResponse,
    ): String {

        if (oauth2AuthorizedClient != null) {
            val clientRegistration = oauth2AuthorizedClient.clientRegistration
            val accessToken = oauth2AuthorizedClient.accessToken

            val oauth2User =
                oauth2UserService.loadUser(OAuth2UserRequest(oauth2AuthorizedClient.clientRegistration, accessToken))

            val authorities = authorityMapper.mapAuthorities(oauth2User.authorities)
            val oauth2AuthenticationToken =
                OAuth2AuthenticationToken(oauth2User, authorities, clientRegistration.registrationId)
            SecurityContextHolder.getContextHolderStrategy().context.authentication = oauth2AuthenticationToken

            oauth2AuthorizationSuccessHandler.onAuthorizationSuccess(
                oauth2AuthorizedClient,
                oauth2AuthenticationToken,
                createAttributes(servletRequest, servletResponse
                )
            )
            model.addAttribute("oauth2AuthenticationToken", oauth2AuthenticationToken)
            model.addAttribute("accessToken", oauth2AuthorizedClient.accessToken.tokenValue)
            model.addAttribute("refreshToken", oauth2AuthorizedClient.refreshToken!!.tokenValue)
        }
        return "home"
    }

}
