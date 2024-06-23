package com.ttasjwi.oauth2.model.users

import org.springframework.security.core.GrantedAuthority

interface ProviderUser {
    val id: String
    val username: String
    val password: String
    val email: String
    val provider: String
    val authorities: MutableCollection<out GrantedAuthority>
    val attributes: MutableMap<String, Any>
    var isCertificated: Boolean

    fun changeCertificated(certificated: Boolean) {
        this.isCertificated = certificated
    }
}
