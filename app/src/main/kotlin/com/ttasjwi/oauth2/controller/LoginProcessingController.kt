package com.ttasjwi.oauth2.controller

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager
import org.springframework.security.oauth2.client.registration.ClientRegistration
import org.springframework.security.oauth2.core.AuthorizationGrantType
import org.springframework.security.oauth2.core.OAuth2Token
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import java.time.Clock
import java.time.Duration

@Controller
class LoginProcessingController(
    private val oauth2AuthorizedClientManager: OAuth2AuthorizedClientManager,
) {

    private val authorityMapper = SimpleAuthorityMapper()
    private val clock = Clock.systemUTC()
    private val clockSkew = Duration.ofSeconds(3600) // 3600초

    init {
        authorityMapper.setPrefix("SYSTEM_")
    }

    @GetMapping("/oauth2Login")
    fun oauth2Login(request: HttpServletRequest, response: HttpServletResponse, model: Model): String {
        // 익명사용자
        val authentication = SecurityContextHolder.getContextHolderStrategy().context.authentication

        // 자격 증명 발급
        var oauth2AuthorizeRequest = OAuth2AuthorizeRequest
            .withClientRegistrationId("keycloak")
            .principal(authentication)
            .attribute(HttpServletRequest::class.java.name, request)
            .attribute(HttpServletResponse::class.java.name, response)
            .build()

        var oauth2AuthorizedClient = oauth2AuthorizedClientManager.authorize(oauth2AuthorizeRequest)


        // 권한부여 타입을 변경하지 않고 액세스 토큰을 재발급하는 방식
        if (oauth2AuthorizedClient != null && hasTokenExpired(oauth2AuthorizedClient.accessToken)
            && oauth2AuthorizedClient.refreshToken != null) {
            oauth2AuthorizedClient = oauth2AuthorizedClientManager.authorize(oauth2AuthorizeRequest)
        }

        // 권한 부여 타입을 런타임에 변경하고 토큰 재발급
//        if (oauth2AuthorizedClient != null && hasTokenExpired(oauth2AuthorizedClient.accessToken)
//            && oauth2AuthorizedClient.refreshToken != null
//        ) {
//
//            val newClientRegistration = ClientRegistration
//                .withClientRegistration(oauth2AuthorizedClient.clientRegistration)
//                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
//                .build()
//
//            oauth2AuthorizedClient = OAuth2AuthorizedClient(
//                newClientRegistration,
//                oauth2AuthorizedClient.principalName,
//                oauth2AuthorizedClient.accessToken,
//                oauth2AuthorizedClient.refreshToken
//            )
//
//            oauth2AuthorizeRequest = OAuth2AuthorizeRequest.withAuthorizedClient(oauth2AuthorizedClient)
//                .principal(authentication)
//                .attribute(HttpServletRequest::class.java.name, request)
//                .attribute(HttpServletResponse::class.java.name, response)
//                .build()
//
//            oauth2AuthorizedClient = oauth2AuthorizedClientManager.authorize(oauth2AuthorizeRequest)
//        }
        model.addAttribute("accessToken", oauth2AuthorizedClient!!.accessToken.tokenValue)
        model.addAttribute("refreshToken", oauth2AuthorizedClient.refreshToken!!.tokenValue)
        return "home"
    }

    private fun hasTokenExpired(token: OAuth2Token): Boolean {
        // 실제 만료시간보다 clockSkew 만큼 차감한 시간을 만료시간으로 간주
        return this.clock.instant().isAfter(token.expiresAt!!.minus(this.clockSkew))
    }
}
