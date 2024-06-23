package com.ttasjwi.oauth2.model.users.form

import com.ttasjwi.oauth2.model.users.ProviderUser
import org.springframework.security.core.GrantedAuthority

class FormProviderUser(
    override val id: String,
    override val username: String,
    override val password: String,
    override val email: String,
    override val provider: String,
    override val authorities: MutableCollection<out GrantedAuthority>,
) : ProviderUser {

    override val attributes: MutableMap<String, Any> = mutableMapOf()

    override var isCertificated = true
}
