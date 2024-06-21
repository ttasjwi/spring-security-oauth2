package com.ttasjwi.oauth2.model

import org.springframework.security.core.GrantedAuthority

interface ProviderUser {
    val id: String
    val username: String
    val password: String
    val email: String
    val provider: String
    val authorities: Collection<out GrantedAuthority>
    val attributes: Map<String, Any>
}
