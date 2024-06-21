package com.ttasjwi.oauth2.model

import org.springframework.security.oauth2.client.registration.ClientRegistration
import org.springframework.security.oauth2.core.user.OAuth2User

class NaverUser(
    oauth2User: OAuth2User,
    clientRegistration: ClientRegistration
) : OAuth2ProviderUser(
    oauth2User.attributes["response"] as Map<String, Any>,
    oauth2User, clientRegistration)  {

    override val id: String
        get() = attributes["id"] as String

    override val username: String
        get() = attributes["email"] as String
}
