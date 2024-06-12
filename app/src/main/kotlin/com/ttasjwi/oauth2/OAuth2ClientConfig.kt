package com.ttasjwi.oauth2

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.oauth2.client.oidc.web.logout.OidcClientInitiatedLogoutSuccessHandler
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler

@Configuration
class OAuth2ClientConfig(
    private val clientRegistrationRepository: ClientRegistrationRepository
) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            authorizeHttpRequests {
                authorize(anyRequest, permitAll)
            }
            oauth2Login {}
            logout {
                // 로그아웃 핸들러 설정
                invalidateHttpSession = true
                clearAuthentication = true
                deleteCookies("JSESSIONID")


                // 로그아웃 성공 후 핸들러 설정
                logoutSuccessHandler = oidcLogoutSuccessHandler()

                permitAll()
            }
        }
        return http.build()
    }

    @Bean
    fun oidcLogoutSuccessHandler(): LogoutSuccessHandler {
        val successHandler = OidcClientInitiatedLogoutSuccessHandler(clientRegistrationRepository)
        successHandler.setPostLogoutRedirectUri("http://localhost:8081/login")
        return successHandler
    }
}
