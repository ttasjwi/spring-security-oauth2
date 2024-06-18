package com.ttasjwi.oauth2.config

import com.ttasjwi.oauth2.security.CustomOAuth2AuthorizationSuccessHandler
import jakarta.servlet.http.HttpServletRequest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.oauth2.client.OAuth2AuthorizationContext
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames
import org.springframework.util.StringUtils
import java.util.function.Function


@Configuration
class OAuth2AuthorizedClientManagerConfig {

    @Bean
    fun oauth2AuthorizedClientManager(
        clientRegistrationRepository: ClientRegistrationRepository,
        oauth2AuthorizedClientRepository: OAuth2AuthorizedClientRepository,

        ): OAuth2AuthorizedClientManager {

        val oauth2AuthorizedClientManager =
            DefaultOAuth2AuthorizedClientManager(clientRegistrationRepository, oauth2AuthorizedClientRepository)

        val oauth2AuthorizedClientProvider =
            OAuth2AuthorizedClientProviderBuilder.builder()
                .authorizationCode()
                .password() // deprecated
                .clientCredentials()
                .refreshToken()
                .build()

        oauth2AuthorizedClientManager.setAuthorizedClientProvider(oauth2AuthorizedClientProvider)
        oauth2AuthorizedClientManager.setAuthorizationSuccessHandler(
            CustomOAuth2AuthorizationSuccessHandler(
                oauth2AuthorizedClientRepository
            )
        )
        oauth2AuthorizedClientManager.setContextAttributesMapper(contextAttributesMapper())
        return oauth2AuthorizedClientManager
    }

    private fun contextAttributesMapper(): Function<OAuth2AuthorizeRequest, Map<String, Any>> {
        return Function<OAuth2AuthorizeRequest, Map<String, Any>> { oAuth2AuthorizeRequest: OAuth2AuthorizeRequest ->
            val contextAttributes = mutableMapOf<String, Any>()

            val request = oAuth2AuthorizeRequest.getAttribute(HttpServletRequest::class.java.name) as HttpServletRequest
            val username: String = request.getParameter(OAuth2ParameterNames.USERNAME)
            val password: String = request.getParameter(OAuth2ParameterNames.PASSWORD)

            if (StringUtils.hasText(username) && StringUtils.hasText(password)) {
                contextAttributes[OAuth2AuthorizationContext.USERNAME_ATTRIBUTE_NAME] = username
                contextAttributes[OAuth2AuthorizationContext.PASSWORD_ATTRIBUTE_NAME] = password
            }
            contextAttributes
        }
    }
}
