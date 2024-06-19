package com.ttasjwi.oauth2.security

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.client.OAuth2AuthorizationSuccessHandler
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter

class CustomOAuth2LoginAuthenticationFilter(
    private val oauth2AuthorizedClientManager: DefaultOAuth2AuthorizedClientManager,
    authorizedClientRepository: OAuth2AuthorizedClientRepository
) : AbstractAuthenticationProcessingFilter(
    DEFAULT_FILTER_PROCESSES_URI,
) {

    private val authorityMapper = SimpleAuthorityMapper()
    private val oauth2AuthorizationSuccessHandler: OAuth2AuthorizationSuccessHandler
    private val oauth2UserService = DefaultOAuth2UserService()

    init {
        authorityMapper.setPrefix("SYSTEM_")
        oauth2AuthorizationSuccessHandler =
            OAuth2AuthorizationSuccessHandler { authorizedClient, authentication, attributes ->
                authorizedClientRepository.saveAuthorizedClient(
                    authorizedClient, authentication,
                    attributes[HttpServletRequest::class.java.name] as HttpServletRequest?,
                    attributes[HttpServletResponse::class.java.name] as HttpServletResponse?
                )
            }
        oauth2AuthorizedClientManager.setAuthorizationSuccessHandler(oauth2AuthorizationSuccessHandler)
    }

    companion object {
        const val DEFAULT_FILTER_PROCESSES_URI: String = "/oauth2Login/**"

        private fun createAttributes(servletRequest: HttpServletRequest, servletResponse: HttpServletResponse): Map<String, Any> {
            return mapOf(
                HttpServletRequest::class.java.name to servletRequest,
                HttpServletResponse::class.java.name to servletResponse
            )
        }
    }

    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication? {
        val authentication = SecurityContextHolder.getContextHolderStrategy().context.authentication
            ?: AnonymousAuthenticationToken(
                "anonymous",
                "anonymousUser",
                AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS")
            )

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
            val refreshToken = oauth2AuthorizedClient.refreshToken

            val oauth2User = oauth2UserService.loadUser(OAuth2UserRequest(clientRegistration, accessToken))

            val authorities = authorityMapper.mapAuthorities(oauth2User.authorities)
            val oauth2AuthenticationToken =
                OAuth2AuthenticationToken(oauth2User, authorities, clientRegistration.registrationId)

            // 인증된 최종사용자 정보를를 다시 저장하기 위해 successHandler를 수동으로 다시 호출
            oauth2AuthorizationSuccessHandler.onAuthorizationSuccess(
                oauth2AuthorizedClient,
                oauth2AuthenticationToken,
                createAttributes(request, response)
            )
            return oauth2AuthenticationToken
        }
        return null
    }

}
