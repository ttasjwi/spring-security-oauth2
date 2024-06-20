package com.ttasjwi.oauth2.config

import org.springframework.boot.autoconfigure.security.servlet.PathRequest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint
import org.springframework.security.web.savedrequest.NullRequestCache
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.security.web.util.matcher.OrRequestMatcher
import org.springframework.security.web.util.matcher.RequestMatcher

@Configuration
class SecurityConfig {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            authorizeHttpRequests {
                authorize(staticPathRequestMatcher(), permitAll)
                authorize("/", permitAll)
                authorize("/favicon.ico", permitAll)
                authorize("/error", permitAll)
                authorize(anyRequest, authenticated)
            }
            oauth2Login {}
            logout {
                logoutSuccessUrl = "/"
            }
        }
        return http.build()
    }

    private fun staticPathRequestMatcher(): RequestMatcher {
        return OrRequestMatcher(
            AntPathRequestMatcher("/static/js/**"),
            AntPathRequestMatcher("/static/images/**"),
            AntPathRequestMatcher("/static/css/**"),
            AntPathRequestMatcher("/static/scss/**"),
        )
    }
}
