package com.ttasjwi.oauth2.service

import com.ttasjwi.oauth2.converter.ProviderUserConverter
import com.ttasjwi.oauth2.converter.ProviderUserRequest
import com.ttasjwi.oauth2.model.users.ProviderUser
import com.ttasjwi.oauth2.repository.UserRepository
import com.ttasjwi.oauth2.support.logging.getLogger
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service

@Service
class CustomOauth2UserService(
    userRepository: UserRepository,
    providerUserConverter: ProviderUserConverter
) : OAuth2UserService<OAuth2UserRequest, OAuth2User>, AbstractOAuth2UserService(userRepository, providerUserConverter) {

    private val oauth2UserService = DefaultOAuth2UserService()

    companion object {
        private val log = getLogger(CustomOauth2UserService::class.java)
    }

    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {
        log.info { "use oauth2UserService" }

        val clientRegistration = userRequest.clientRegistration

        val oauth2User = oauth2UserService.loadUser(userRequest)

        val providerUserRequest = ProviderUserRequest.fromSocialUser(clientRegistration, oauth2User)

        val providerUser = super.providerUser(providerUserRequest)

        log.info { "providerUser.id = ${providerUser!!.id}" }
        log.info { "providerUser.username = ${providerUser!!.username}" }
        log.info { "providerUser.email = ${providerUser!!.email}" }

        // 회원가입
        super.registerUser(providerUser!!, userRequest)

        return oauth2User
    }
}
