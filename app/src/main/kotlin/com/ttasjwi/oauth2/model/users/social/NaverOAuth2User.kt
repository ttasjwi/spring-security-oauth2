package com.ttasjwi.oauth2.model.users.social

import org.springframework.security.oauth2.client.registration.ClientRegistration
import org.springframework.security.oauth2.core.user.OAuth2User

class NaverOAuth2User(
    oauth2User: OAuth2User,
    clientRegistration: ClientRegistration
) : OAuth2ProviderUser(
    oauth2User.attributes,
    oauth2User, clientRegistration)  {

    override val id: String
        get() = (attributes["response"] as Map<*, *>)["id"] as String

    override val username: String
        get() = (attributes["response"] as Map<*, *>)["email"] as String


    override val email: String
        get() = (attributes["response"] as Map<*, *>)["email"] as String
}
