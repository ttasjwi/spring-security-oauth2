package com.ttasjwi.oauth2.service

import com.ttasjwi.oauth2.converter.ProviderUserConverter
import com.ttasjwi.oauth2.converter.ProviderUserRequest
import com.ttasjwi.oauth2.repository.UserRepository
import com.ttasjwi.oauth2.support.logging.getLogger
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.oidc.user.OidcUser
import org.springframework.stereotype.Service

@Service
class CustomOidcUserService(
    userRepository: UserRepository,
    providerUserConverter: ProviderUserConverter
) : OAuth2UserService<OidcUserRequest, OidcUser>, AbstractOAuth2UserService(userRepository, providerUserConverter) {

    private val oidcUserService = OidcUserService()

    companion object {
        private val log = getLogger(CustomOidcUserService::class.java)
    }

    override fun loadUser(userRequest: OidcUserRequest): OidcUser {
        log.info { "use oidcUserService" }

        val clientRegistration = userRequest.clientRegistration

        val oauth2User = oidcUserService.loadUser(userRequest)

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
