package com.ttasjwi.oauth2.service

import com.ttasjwi.oauth2.repository.UserRepository
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service

@Service
class CustomOauth2UserService(
    userRepository: UserRepository,
) : OAuth2UserService<OAuth2UserRequest, OAuth2User>, AbstractOAuth2UserService(userRepository) {

    private val oauth2UserService = DefaultOAuth2UserService()

    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {
        val clientRegistration = userRequest.clientRegistration

        val oauth2User = oauth2UserService.loadUser(userRequest)
        val providerUser = super.providerUser(clientRegistration, oauth2User)

        // 회원가입
        super.registerUser(providerUser!!, userRequest)

        return oauth2User
    }

}
