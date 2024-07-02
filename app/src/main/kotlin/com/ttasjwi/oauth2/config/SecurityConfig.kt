package com.ttasjwi.oauth2.config

import com.ttasjwi.oauth2.security.filter.CustomLoginAuthenticationFilter
import com.ttasjwi.oauth2.security.filter.MacJwtAuthenticationFilter
import com.ttasjwi.oauth2.security.signature.JWKRepository
import com.ttasjwi.oauth2.security.signature.TokenSigner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.NoOpPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

@Configuration
class SecurityConfig(
    private val tokenSigner: TokenSigner,
    private val jwkRepository: JWKRepository
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
            addFilterBefore<UsernamePasswordAuthenticationFilter>(macJwtAuthenticationFilter())
        }
        return http.build()
    }

    private fun customLoginAuthenticationFilter(): CustomLoginAuthenticationFilter {
        val filter = CustomLoginAuthenticationFilter(AntPathRequestMatcher("/login", HttpMethod.POST.name()), tokenSigner)
        filter.setAuthenticationManager(authenticationManager())
        return filter
    }

    private fun macJwtAuthenticationFilter(): MacJwtAuthenticationFilter {
        return MacJwtAuthenticationFilter(jwkRepository)
    }

    @Bean
    fun authenticationManager(): AuthenticationManager {
        val provider = DaoAuthenticationProvider()
        provider.setUserDetailsService(userDetailsService())
        provider.setPasswordEncoder(passwordEncoder())

        return ProviderManager(provider)
    }

    @Bean
    fun userDetailsService(): UserDetailsService {
        val user = User.withUsername("user").password("1111").authorities("ROLE_USER").build()
        return InMemoryUserDetailsManager(user)
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = NoOpPasswordEncoder.getInstance()

}
