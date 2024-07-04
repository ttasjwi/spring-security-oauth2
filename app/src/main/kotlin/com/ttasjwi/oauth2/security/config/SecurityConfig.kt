package com.ttasjwi.oauth2.security.config

import com.ttasjwi.oauth2.security.authorization.CustomRoleConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableMethodSecurity
class SecurityConfig {

    @Bean
    @Order(0)
    fun securityFilterChain1(http: HttpSecurity): SecurityFilterChain {
        http {
            securityMatcher("/photos/1")
            authorizeHttpRequests {
                authorize(HttpMethod.GET, "/photos/1", hasAuthority("ROLE_photo"))
            }
            oauth2ResourceServer {
                jwt {
                    jwtAuthenticationConverter = jwtAuthenticationConverter()
                }
            }
        }
        return http.build()
    }


    @Bean
    @Order(1)
    fun securityFilterChain2(http: HttpSecurity): SecurityFilterChain {
        http {
            securityMatcher("/photos/*")
            authorizeHttpRequests {
                authorize(HttpMethod.GET, "/photos/*", permitAll)
            }
            oauth2ResourceServer {
                jwt {
                    jwtAuthenticationConverter = jwtAuthenticationConverter()
                }
            }
        }
        return http.build()
    }

    @Bean
    fun jwtAuthenticationConverter(): JwtAuthenticationConverter {
        val jwtConverter = JwtAuthenticationConverter()
        jwtConverter.setJwtGrantedAuthoritiesConverter(CustomRoleConverter())

        return jwtConverter
    }
}
