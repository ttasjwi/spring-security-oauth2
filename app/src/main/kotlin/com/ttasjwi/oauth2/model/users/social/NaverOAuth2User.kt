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

    /**
     * naver -> response/name : 회원 이름
     */
    override val username: String
        get() = (attributes["response"] as Map<*, *>)["name"] as String


    override val email: String
        get() = (attributes["response"] as Map<*, *>)["email"] as String

    override var isCertificated = true
}
