package com.ttasjwi.oauth2

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint
import org.springframework.security.web.savedrequest.NullRequestCache

@Configuration
class OAuth2ClientConfig {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            authorizeHttpRequests {
                authorize("/", permitAll)
                authorize(anyRequest, authenticated)
            }
            oauth2Client {}
            exceptionHandling {
                authenticationEntryPoint = LoginUrlAuthenticationEntryPoint("/oauth2/authorization/keycloak")
            }
            requestCache {
                requestCache = NullRequestCache()
            }
        }
        return http.build()
    }
}
