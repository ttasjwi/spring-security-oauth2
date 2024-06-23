package com.ttasjwi.oauth2.config

import com.ttasjwi.oauth2.converter.ProviderUserConverter
import com.ttasjwi.oauth2.model.users.User
import com.ttasjwi.oauth2.repository.UserRepository
import com.ttasjwi.oauth2.service.CustomUserDetailsService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.userdetails.UserDetailsService


@Configuration
class UserManagementConfig {

    @Bean
    fun userDetailsService(userRepository: UserRepository, providerUserConverter: ProviderUserConverter): UserDetailsService {
        return CustomUserDetailsService(userRepository, providerUserConverter)
    }

    @Bean
    fun userRepository() : UserRepository {
        val userRepository =  UserRepository()
        val sampleUser = User(
            registrationId = "none",
            id = "1111212347",
            username = "test",
            password = "{noop}1111",
            authorities = AuthorityUtils.createAuthorityList("ROLE_USER"),
            email = "ttasjwi920@gmail.com",
            provider = "none"
        )
        userRepository.save(sampleUser)
        return userRepository
    }
}
