package com.ttasjwi.oauth2.model.users.social

import org.springframework.security.oauth2.client.registration.ClientRegistration
import org.springframework.security.oauth2.core.user.OAuth2User

class GoogleOidcUser(
    oauth2User: OAuth2User,
    clientRegistration: ClientRegistration
) : OAuth2ProviderUser(oauth2User.attributes, oauth2User, clientRegistration) {

    override val id: String
        get() = super.attributes["sub"] as String

    /**
     * 사용자의 전체 이름: id_token -> name
     */
    override val username: String
        get() = super.attributes["name"] as String


    override val email: String
        get() = super.attributes["email"] as String

    override var isCertificated = false
}
