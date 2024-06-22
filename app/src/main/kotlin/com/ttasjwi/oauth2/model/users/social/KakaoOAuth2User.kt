package com.ttasjwi.oauth2.model.users.social

import org.springframework.security.oauth2.client.registration.ClientRegistration
import org.springframework.security.oauth2.core.user.OAuth2User

class KakaoOAuth2User(
    oauth2User: OAuth2User,
    clientRegistration: ClientRegistration
) : OAuth2ProviderUser(oauth2User.attributes, oauth2User, clientRegistration) {

    override val id: String
        get() {
            return attributes["id"] as String
        }

    override val username: String
        get() {
            val kakaoAccount = attributes["kakao_account"] as Map<*, *>
            val profile = kakaoAccount["profile"] as Map<*, *>
            return profile["nickname"] as String
        }

    override val email: String
        get() {
            val kakaoAccount = attributes["kakao_account"] as Map<*, *>
            return kakaoAccount["email"] as String
        }
}
