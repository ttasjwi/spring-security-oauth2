package com.ttasjwi.oauth2.security.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.invoke
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
                authorize(HttpMethod.GET, "/photos/1", hasAuthority("SCOPE_photo"))
            }
            oauth2ResourceServer {
                jwt {  }
            }
        }
        return http.build()
    }


    @Bean
    @Order(1)
    fun securityFilterChain2(http: HttpSecurity): SecurityFilterChain {
        http {
            securityMatcher("/photos/2")
            authorizeHttpRequests {
                authorize(HttpMethod.GET, "/photos/2", permitAll)
            }
            oauth2ResourceServer {
                jwt {  }
            }
        }
        return http.build()
    }
}
