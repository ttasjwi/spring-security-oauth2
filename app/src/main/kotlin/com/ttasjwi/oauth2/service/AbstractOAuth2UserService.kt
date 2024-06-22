package com.ttasjwi.oauth2.service

import com.ttasjwi.oauth2.converter.ProviderUserConverter
import com.ttasjwi.oauth2.converter.ProviderUserRequest
import com.ttasjwi.oauth2.model.users.ProviderUser
import com.ttasjwi.oauth2.model.users.User
import com.ttasjwi.oauth2.repository.UserRepository
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest

abstract class AbstractOAuth2UserService(
    private val userRepository: UserRepository,
    private val providerUserConverter: ProviderUserConverter
) {

    fun providerUser(providerUserRequest: ProviderUserRequest): ProviderUser? {
        return providerUserConverter.convert(providerUserRequest)
    }

    fun registerUser(providerUser: ProviderUser, userRequest: OAuth2UserRequest) {
        val user = userRepository.findByUserNameOrNull(providerUser.username)

        if (user == null) {
            userRepository.save(
                User(
                    registrationId = userRequest.clientRegistration.registrationId,
                    id = providerUser.id,
                    username = providerUser.username,
                    password = providerUser.password,
                    provider = providerUser.provider,
                    email = providerUser.email,
                    authorities = providerUser.authorities
                )
            )
        } else {
            println("user = $user")
        }
    }
}
