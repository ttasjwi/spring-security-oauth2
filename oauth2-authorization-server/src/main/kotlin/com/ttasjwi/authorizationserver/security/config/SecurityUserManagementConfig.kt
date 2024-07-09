package com.ttasjwi.authorizationserver.security.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.provisioning.InMemoryUserDetailsManager

@Configuration
class SecurityUserManagementConfig {

    @Bean
    fun userDetailsService(): UserDetailsService {
        val user = User.builder()
            .username("user")
            .password("{noop}1111")
            .roles("USER")
            .build()
        return InMemoryUserDetailsManager(user)
    }
}
