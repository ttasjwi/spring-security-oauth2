package com.ttasjwi.oauth2.converter

import com.ttasjwi.oauth2.model.users.User
import org.springframework.security.oauth2.client.registration.ClientRegistration
import org.springframework.security.oauth2.core.user.OAuth2User

class ProviderUserRequest

private constructor(
    val clientRegistration: ClientRegistration? = null,
    val oAuth2User: OAuth2User? = null,
    val user: User?
) {

    companion object {

        fun fromSocialUser(clientRegistration: ClientRegistration, oAuth2User: OAuth2User): ProviderUserRequest {
            return ProviderUserRequest(clientRegistration, oAuth2User, null)
        }

        fun fromFormUser(user: User): ProviderUserRequest {
            return ProviderUserRequest(null, null, user)
        }
    }
}
