package com.ttasjwi.oauth2.service

import com.ttasjwi.oauth2.repository.UserRepository
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.oidc.user.OidcUser
import org.springframework.stereotype.Service

@Service
class CustomOidcUserService(
    userRepository: UserRepository
) : OAuth2UserService<OidcUserRequest, OidcUser>, AbstractOAuth2UserService(userRepository) {

    private val oidcUserService = OidcUserService()

    override fun loadUser(userRequest: OidcUserRequest): OidcUser {
        val clientRegistration = userRequest.clientRegistration

        val oauth2User = oidcUserService.loadUser(userRequest)
        val providerUser = super.providerUser(clientRegistration, oauth2User)

        // 회원가입
        super.registerUser(providerUser!!, userRequest)

        return oauth2User
    }
}
