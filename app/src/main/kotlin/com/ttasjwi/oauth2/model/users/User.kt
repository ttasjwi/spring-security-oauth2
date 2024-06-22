package com.ttasjwi.oauth2.model.users

import org.springframework.security.core.GrantedAuthority

class User(
    val registrationId: String,
    val id: String,
    val username: String,
    val password: String,
    val provider: String,
    val email: String,
    val authorities: Collection<out GrantedAuthority>
)
