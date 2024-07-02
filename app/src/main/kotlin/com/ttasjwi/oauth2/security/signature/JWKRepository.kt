package com.ttasjwi.oauth2.security.signature

import com.nimbusds.jose.jwk.JWK

class JWKRepository(
    private val jwk: JWK
) {

    fun findJWK(): JWK {
        return jwk
    }
}
