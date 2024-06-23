package com.ttasjwi.oauth2.service

import com.ttasjwi.oauth2.converter.ProviderUserConverter
import com.ttasjwi.oauth2.converter.ProviderUserRequest
import com.ttasjwi.oauth2.model.users.AuthUser
import com.ttasjwi.oauth2.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException

class CustomUserDetailsService(
    private val userRepository: UserRepository,
    private val providerUserConverter: ProviderUserConverter
): UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByUserNameOrNull(username) ?: throw UsernameNotFoundException("일치하는 회원 찾을 수 없음 ")

        val providerUserRequest = ProviderUserRequest.fromFormUser(user)
        val providerUser = providerUserConverter.convert(providerUserRequest)

        return AuthUser(providerUser!!)
    }
}
