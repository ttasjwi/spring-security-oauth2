package com.ttasjwi.oauth2

import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestCustomizers
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.security.web.util.matcher.RequestMatcher
import java.util.function.Consumer

class CustomOAuth2AuthorizationRequestResolver
private constructor(
    private val authorizationRequestMatcher: RequestMatcher,
    private val defaultResolver: DefaultOAuth2AuthorizationRequestResolver
) : OAuth2AuthorizationRequestResolver {

    companion object {
        private val REGISTRATION_ID_URI_VARIABLE_NAME = "registrationId";
        private val DEFAULT_PKCE_APPLIER: Consumer<OAuth2AuthorizationRequest.Builder> =
            OAuth2AuthorizationRequestCustomizers.withPkce()


        fun of(
            clientRegistrationRepository: ClientRegistrationRepository,
            authorizationRequestBaseUri: String
        ): CustomOAuth2AuthorizationRequestResolver {
            return CustomOAuth2AuthorizationRequestResolver(
                AntPathRequestMatcher("${authorizationRequestBaseUri}/{$REGISTRATION_ID_URI_VARIABLE_NAME}"),
                DefaultOAuth2AuthorizationRequestResolver(clientRegistrationRepository, authorizationRequestBaseUri)
            )
        }
    }

    override fun resolve(request: HttpServletRequest): OAuth2AuthorizationRequest? {
        return resolve(request, resolveRegistrationId(request))
    }

    override fun resolve(request: HttpServletRequest, clientRegistrationId: String?): OAuth2AuthorizationRequest? {
        if (clientRegistrationId == null) {
            return null
        }

        // registrationId 가 "keyCloakWithPKCE" 인 경우 여기서 요청을 가로챈다.
        // 기본 스프링 시큐리티 구현은 clientAuthenticationMethod = none 일 때만 PKCE가 작동한다.
        // 기밀 클라이언트로서 pkce 를 적용시키려면 우리가 수동으로 pkce를 적용시켜줘야한다.
        if (clientRegistrationId == "keycloakWithPKCE") {
            return resolveKeyCloakPKCERequest(request)
        }
        return defaultResolver.resolve(request)
    }

    private fun resolveKeyCloakPKCERequest(request: HttpServletRequest): OAuth2AuthorizationRequest {
        val authorizationRequest = defaultResolver.resolve(request)
        val builder = OAuth2AuthorizationRequest.from(authorizationRequest)

        // 커스텀 헤더 전달 가능
        val additionalParameters = mapOf(
            "customName1" to "customValue1",
            "customName2" to "customValue2",
            "customName3" to "customValue3"
        )
        builder.additionalParameters(additionalParameters)

        DEFAULT_PKCE_APPLIER.accept(builder)
        return builder.build()
    }

    private fun resolveRegistrationId(request: HttpServletRequest): String? {
        if (authorizationRequestMatcher.matches(request)) {
            return this.authorizationRequestMatcher.matcher(request).variables[REGISTRATION_ID_URI_VARIABLE_NAME]
        }
        return null
    }

}
