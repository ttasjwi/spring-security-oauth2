package com.ttasjwi.album.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
class AlbumServerSecurityConfig {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            authorizeHttpRequests {
                authorize("/photos", hasAuthority("SCOPE_photo"))
                authorize("/myInfo", hasAuthority("SCOPE_photo"))
                authorize(anyRequest, authenticated)
            }
            cors {
                configurationSource = corsConfigurationSource()
            }
            oauth2ResourceServer {
                jwt {  }
            }
        }
        return http.build()
    }

    private fun corsConfigurationSource(): CorsConfigurationSource {
        val corsConfiguration = CorsConfiguration()
        corsConfiguration.addAllowedOrigin("http://127.0.0.1:8081")
        corsConfiguration.addAllowedMethod("*")
        corsConfiguration.addAllowedHeader("*")
        corsConfiguration.allowCredentials = true
        corsConfiguration.maxAge = 3600L

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", corsConfiguration)
        return source
    }
}
