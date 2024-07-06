package com.ttasjwi.resourceserver.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
class OAuth2ResourceServerConfig {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            authorizeHttpRequests {
                authorize("/photos", hasAuthority("SCOPE_photo"))
                authorize(anyRequest, authenticated)
            }
            oauth2ResourceServer {
                jwt {}
            }
            cors {
                configurationSource = corsConfigurationSource()
            }
        }
        return http.build()
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val corsConfiguration = CorsConfiguration()
        corsConfiguration.addAllowedOrigin("http://localhost:8081")
        corsConfiguration.addAllowedMethod("*")
        corsConfiguration.addAllowedHeader("*")
        corsConfiguration.allowCredentials = true
        corsConfiguration.maxAge = 3600L

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", corsConfiguration)
        return source
    }

}
