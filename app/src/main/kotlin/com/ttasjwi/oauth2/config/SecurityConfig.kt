package com.ttasjwi.oauth2.config

import com.ttasjwi.oauth2.security.CustomOAuth2LoginAuthenticationFilter
import org.springframework.boot.autoconfigure.security.servlet.PathRequest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.context.HttpSessionSecurityContextRepository
import org.springframework.security.web.savedrequest.NullRequestCache

@Configuration
class SecurityConfig(
    private val oauth2AuthorizedClientManager: DefaultOAuth2AuthorizedClientManager,
    private val oAuth2AuthorizedClientRepository: OAuth2AuthorizedClientRepository
) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            authorizeHttpRequests {
                authorize(PathRequest.toStaticResources().atCommonLocations(), permitAll)
                authorize("/", permitAll)
                authorize("/client", permitAll)
                authorize("/favicon.ico", permitAll)
                authorize("/error", permitAll)
                authorize(anyRequest, authenticated)
            }
            addFilterBefore<UsernamePasswordAuthenticationFilter>(customOAuth2LoginAuthenticationFilter())
            oauth2Client {  }
            logout {
                disable()
            }
            requestCache {
                requestCache = NullRequestCache()
            }
            exceptionHandling {
                authenticationEntryPoint = LoginUrlAuthenticationEntryPoint("/")
            }
        }
        return http.build()
    }

    fun customOAuth2LoginAuthenticationFilter(): CustomOAuth2LoginAuthenticationFilter {
        val filter =  CustomOAuth2LoginAuthenticationFilter(oauth2AuthorizedClientManager, oAuth2AuthorizedClientRepository)
        filter.setAuthenticationSuccessHandler(SimpleUrlAuthenticationSuccessHandler("/home"))
        filter.setSecurityContextRepository(HttpSessionSecurityContextRepository())
        return filter
    }
}
