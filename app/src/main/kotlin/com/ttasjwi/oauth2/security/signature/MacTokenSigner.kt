package com.ttasjwi.oauth2.security.signature

import com.nimbusds.jose.JWSSigner
import com.nimbusds.jose.crypto.MACSigner
import com.nimbusds.jose.jwk.JWK
import com.nimbusds.jose.jwk.OctetSequenceKey

class MacTokenSigner(
    private val jwkRepository: JWKRepository): AbstractTokenSigner() {


    override fun getJWK(): JWK {
        return jwkRepository.findJWK()
    }


    override fun getJWSSigner(jwk: JWK): JWSSigner {
        return MACSigner((jwk as OctetSequenceKey).toSecretKey())
    }

}
