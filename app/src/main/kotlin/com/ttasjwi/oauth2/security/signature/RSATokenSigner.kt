package com.ttasjwi.oauth2.security.signature

import com.nimbusds.jose.JWSSigner
import com.nimbusds.jose.crypto.RSASSASigner
import com.nimbusds.jose.jwk.JWK
import com.nimbusds.jose.jwk.RSAKey

class RSATokenSigner(jwkRepository: JWKRepository) : TokenSigner(jwkRepository) {

    override fun getJWSSigner(jwk: JWK): JWSSigner {
        return RSASSASigner((jwk as RSAKey).toRSAPrivateKey())
    }
}
