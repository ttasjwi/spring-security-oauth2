package com.ttasjwi.oauth2.service

import com.ttasjwi.oauth2.model.*
import com.ttasjwi.oauth2.repository.UserRepository
import org.springframework.security.oauth2.client.registration.ClientRegistration
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.OAuth2User

abstract class AbstractOAuth2UserService(
    private val userRepository: UserRepository,
) {

    fun providerUser(clientRegistration: ClientRegistration, oauth2User: OAuth2User): ProviderUser? {
        val registrationId = clientRegistration.registrationId

        return when (registrationId) {
            "google" -> GoogleUser(oauth2User, clientRegistration)
            "keycloak" -> KeycloakUser(oauth2User, clientRegistration)
            "naver" -> NaverUser(oauth2User, clientRegistration)
            else -> null
        }
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
