package com.ttasjwi.oauth2.config

import com.ttasjwi.oauth2.security.CustomAuthorityMapper
import com.ttasjwi.oauth2.service.CustomOauth2UserService
import com.ttasjwi.oauth2.service.CustomOidcUserService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.security.web.util.matcher.OrRequestMatcher
import org.springframework.security.web.util.matcher.RequestMatcher

@Configuration
class SecurityConfig(

    private val customOAuth2UserService: CustomOauth2UserService,
    private val customOidcUserService: CustomOidcUserService
) {


    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            authorizeHttpRequests {
                authorize(staticPathRequestMatcher(), permitAll)
                authorize("/", permitAll)
                authorize("/favicon.ico", permitAll)
                authorize("/error", permitAll)
                authorize("/api/user", hasAnyRole("SCOPE_profile", "SCOPE_email"))
                authorize("/api/oidc", hasAnyRole("SCOPE_openid"))
                authorize(anyRequest, authenticated)
            }
            oauth2Login {
                userInfoEndpoint {
                    userService = customOAuth2UserService
                    oidcUserService = customOidcUserService
                }
            }
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

    @Bean
    fun grantedAuthoritiesMapper(): GrantedAuthoritiesMapper {
        return CustomAuthorityMapper()
    }

}
