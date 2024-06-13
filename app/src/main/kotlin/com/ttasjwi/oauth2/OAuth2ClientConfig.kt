package com.ttasjwi.oauth2

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.web.SecurityFilterChain

@Configuration
class OAuth2ClientConfig {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            authorizeHttpRequests {
                authorize("/login", permitAll)
                authorize(anyRequest, authenticated)
            }
            oauth2Login {

                loginPage = "/login"


                // 권한부여 요청(리다이렉트 필터) baseUri 설정
                // 예: /oauth2/v1/authorization/google
                authorizationEndpoint {
                    baseUri = "/oauth2/v1/authorization"
                }

                // OAuth2 로그인 OAuth2LoginAuthenticationFilter 작동 조건
                loginProcessingUrl = "/login/v1/oauth2/code/*"


                // OAuth2 로그인 OAuth2LoginAuthenticationFilter 작동 조건 (우선시)
                redirectionEndpoint {
                    baseUri = "/login/v2/oauth2/code/*"
                }
            }
        }
        return http.build()
    }
}
