package com.ttasjwi.oauth2.security.config

import com.ttasjwi.oauth2.security.filter.CustomLoginAuthenticationFilter
import com.ttasjwi.oauth2.security.filter.JwtDecoderAuthenticationFilter
import com.ttasjwi.oauth2.security.signature.TokenSigner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

@Configuration
class SecurityConfig(
    private val authenticationManager: AuthenticationManager,
    private val tokenSigner: TokenSigner,
    private val jwtDecoder: JwtDecoder
) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            csrf { disable() }
            sessionManagement {
                sessionCreationPolicy = SessionCreationPolicy.STATELESS
            }
            authorizeHttpRequests {
                authorize("/", permitAll)
                authorize(anyRequest, authenticated)
            }
            addFilterBefore<UsernamePasswordAuthenticationFilter>(customLoginAuthenticationFilter())
            addFilterBefore<UsernamePasswordAuthenticationFilter>(jwtDecoderAuthenticationFilter())
        }
        return http.build()
    }

    private fun customLoginAuthenticationFilter(): CustomLoginAuthenticationFilter {
        val filter = CustomLoginAuthenticationFilter(AntPathRequestMatcher("/login", HttpMethod.POST.name()), tokenSigner)
        filter.setAuthenticationManager(authenticationManager)
        return filter
    }

    private fun jwtDecoderAuthenticationFilter(): JwtDecoderAuthenticationFilter {
        return JwtDecoderAuthenticationFilter(jwtDecoder)
    }

}
