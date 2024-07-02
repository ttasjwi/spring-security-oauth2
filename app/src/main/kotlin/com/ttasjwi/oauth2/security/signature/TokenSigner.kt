package com.ttasjwi.oauth2.security.signature

import org.springframework.security.core.userdetails.User

interface TokenSigner {
    fun signToken(user: User): String
}
