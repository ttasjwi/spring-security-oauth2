package com.ttasjwi.authorizationserver.security.config

import com.ttasjwi.authorizationserver.security.authentication.CustomOAuth2AuthorizationCodeRequestAuthenticationProvider
import com.ttasjwi.authorizationserver.security.authentication.CustomOAuth2AuthorizationConsentAuthenticationProvider
import jakarta.servlet.http.HttpServletResponse
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationCodeRequestAuthenticationToken
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint
import org.springframework.util.StringUtils
import org.springframework.web.util.UriComponentsBuilder
import org.springframework.web.util.UriUtils
import java.nio.charset.StandardCharsets

@Configuration
class SecurityFilterChainsConfig(
    private val registeredClientRepository: RegisteredClientRepository,
    private val authorizationService: OAuth2AuthorizationService,
    private val authorizationConsentService: OAuth2AuthorizationConsentService
) {

    /**
     * 인가서버 관련 기능
     *
     */
    @Order(0)
    @Bean
    fun authorizationServerSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
        val authorizationServerConfigurer = OAuth2AuthorizationServerConfigurer()
        authorizationServerConfigurer.oidc(Customizer.withDefaults())
        authorizationServerConfigurer.authorizationEndpoint {
            it
                .authenticationProvider(CustomOAuth2AuthorizationCodeRequestAuthenticationProvider(registeredClientRepository, authorizationService, authorizationConsentService))
                .authenticationProvider(CustomOAuth2AuthorizationConsentAuthenticationProvider(registeredClientRepository, authorizationService, authorizationConsentService))
                .authorizationResponseHandler { request, response, authentication ->
                    val authorizationCodeRequestAuthentication =
                        authentication as OAuth2AuthorizationCodeRequestAuthenticationToken

                    println("코드발급 절차 시작")
                    println("authentication = $authorizationCodeRequestAuthentication")

                    val uriBuilder = UriComponentsBuilder
                        .fromUriString(authorizationCodeRequestAuthentication.redirectUri!!)
                        .queryParam(
                            OAuth2ParameterNames.CODE,
                            authorizationCodeRequestAuthentication.authorizationCode!!.tokenValue
                        )
                    if (StringUtils.hasText(authorizationCodeRequestAuthentication.state)) {
                        uriBuilder.queryParam(
                            OAuth2ParameterNames.STATE,
                            UriUtils.encode(authorizationCodeRequestAuthentication.state!!, StandardCharsets.UTF_8)
                        )
                    }
                    val redirectUri = uriBuilder.build(true).toUriString()
                    response.sendRedirect(redirectUri)
                }
                .errorResponseHandler { request, response, exception ->
                    println(exception.toString())
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST)
                }
        }


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
