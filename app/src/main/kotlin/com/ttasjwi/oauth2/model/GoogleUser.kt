package com.ttasjwi.oauth2.model

import org.springframework.security.oauth2.client.registration.ClientRegistration
import org.springframework.security.oauth2.core.user.OAuth2User

class GoogleUser(
    oauth2User: OAuth2User,
    clientRegistration: ClientRegistration
) : OAuth2ProviderUser(oauth2User.attributes, oauth2User, clientRegistration) {

    override val id: String
        get() = super.attributes["sub"] as String

    override val username: String
        get() = super.attributes["sub"] as String

}
