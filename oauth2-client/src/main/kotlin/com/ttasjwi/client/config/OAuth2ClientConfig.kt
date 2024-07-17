package com.ttasjwi.client.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository
import org.springframework.security.web.SecurityFilterChain

@EnableWebSecurity
@Configuration
class OAuth2ClientConfig(
    private val clientRegistrationRepository: ClientRegistrationRepository,
    private val authorizedClientRepository: OAuth2AuthorizedClientRepository
) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            authorizeHttpRequests {
                authorize("/", permitAll)
                authorize("/home", permitAll)
                authorize(anyRequest, authenticated)
            }
            oauth2Login {
                // oauth2 로그인 성공 후 리다이렉트 페이지
                defaultSuccessUrl("/", true)
            }
        }
        return http.build()
    }

    @Bean
    fun oauth2AuthorizedClientManager(): OAuth2AuthorizedClientManager {
        val provider = OAuth2AuthorizedClientProviderBuilder.builder()
            .authorizationCode()
            .refreshToken()
            .build()

        val manager = DefaultOAuth2AuthorizedClientManager(clientRegistrationRepository, authorizedClientRepository)
        manager.setAuthorizedClientProvider(provider)
        return manager
    }

}
