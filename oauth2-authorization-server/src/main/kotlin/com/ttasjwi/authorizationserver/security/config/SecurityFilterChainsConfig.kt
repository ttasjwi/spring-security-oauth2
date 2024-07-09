package com.ttasjwi.authorizationserver.security.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint

@Configuration
class SecurityFilterChainsConfig {

    /**
     * 인가서버 관련 기능
     *
     */
    @Order(0)
    @Bean
    fun authorizationServerSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
        val authorizationServerConfigurer = OAuth2AuthorizationServerConfigurer()
        authorizationServerConfigurer.oidc(Customizer.withDefaults())

        val endpointsMatcher = authorizationServerConfigurer.endpointsMatcher

        http {
            securityMatcher(endpointsMatcher)
            authorizeHttpRequests {
                authorize(anyRequest, authenticated)
            }
            csrf {
                ignoringRequestMatchers(endpointsMatcher)
            }
            with(authorizationServerConfigurer)
            exceptionHandling {
                authenticationEntryPoint = LoginUrlAuthenticationEntryPoint("/login")
            }
            oauth2ResourceServer {
                jwt { }
            }
        }
        return http.build()
    }

    /**
     * 사용자 로그인("/login") -> Form 로그인 처리
     * 그 외 모든 요청 -> 인증 필요
     */
    @Order(1)
    @Bean
    fun userAuthenticationSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            authorizeHttpRequests {
                authorize(anyRequest, authenticated)
            }
            formLogin { }
        }
        return http.build()
    }
}
