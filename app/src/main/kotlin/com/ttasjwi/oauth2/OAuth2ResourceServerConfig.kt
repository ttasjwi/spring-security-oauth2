package com.ttasjwi.oauth2

import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
class OAuth2ResourceServerConfig(
    private val properties: OAuth2ResourceServerProperties
) {

    @Bean
    fun jwtSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests {
                it.anyRequest().authenticated()
            }
            .oauth2ResourceServer {
                it.jwt(Customizer.withDefaults())
            }
        return http.build()
    }

//    @Bean
//    fun JwtDecoder(): JwtDecoder {
//        return JwtDecoders.fromIssuerLocation(properties.jwt.issuerUri)
////        return JwtDecoders.fromOidcIssuerLocation(properties.jwt.issuerUri)
//    }

    @Bean
    fun JwtDecoder(): JwtDecoder {
        return NimbusJwtDecoder
            .withJwkSetUri(properties.jwt.jwkSetUri)
            // 기본 알고리즘 : RS256
            .jwsAlgorithm(SignatureAlgorithm.RS256).build()
    }
}
