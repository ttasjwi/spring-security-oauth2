package com.ttasjwi.oauth2.model.users.social

import com.ttasjwi.oauth2.model.users.ProviderUser
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.client.registration.ClientRegistration
import org.springframework.security.oauth2.core.user.OAuth2User
import java.util.UUID

abstract class OAuth2ProviderUser(
    private val _attributes: MutableMap<String, Any>,
    private val oAuth2User: OAuth2User,
    private val clientRegistration: ClientRegistration
) : ProviderUser {

    override val password: String
        get() = UUID.randomUUID().toString()

    override val authorities: MutableCollection<out GrantedAuthority>
        get() = oAuth2User.authorities.map { SimpleGrantedAuthority(it.authority) }.toMutableList()

    override val provider: String
        get() = clientRegistration.registrationId

    override val attributes: MutableMap<String, Any> get() = _attributes
}
