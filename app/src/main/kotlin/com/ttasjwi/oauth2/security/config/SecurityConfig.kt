package com.ttasjwi.oauth2.security.config

import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.oauth2.server.resource.introspection.NimbusOpaqueTokenIntrospector
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableMethodSecurity
class SecurityConfig {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            authorizeHttpRequests {
                authorize(anyRequest, authenticated)
            }
            oauth2ResourceServer {
                opaqueToken { }
            }
        }
        return http.build()
    }

    @Bean
    fun opaqueTokenIntrospector(properties: OAuth2ResourceServerProperties): OpaqueTokenIntrospector {
        val opaqueToken = properties.opaquetoken
        return NimbusOpaqueTokenIntrospector(opaqueToken.introspectionUri, opaqueToken.clientId, opaqueToken.clientSecret)
    }

}
