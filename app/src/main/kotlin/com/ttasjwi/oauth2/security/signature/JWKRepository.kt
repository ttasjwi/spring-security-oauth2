package com.ttasjwi.oauth2.security.signature

import com.nimbusds.jose.JWSAlgorithm
import com.nimbusds.jose.jwk.JWK
import com.nimbusds.jose.jwk.RSAKey
import java.io.FileInputStream
import java.security.KeyStore

class JWKRepository(
    jksPath: String,
    alias: String,
    pin: CharArray
) {
    private val jwk: JWK

    init {
        val inputStream = FileInputStream(jksPath)
        val keyStore = KeyStore.getInstance(KeyStore.getDefaultType())
        keyStore.load(inputStream, pin)

        jwk = RSAKey.Builder(RSAKey.load(keyStore, alias, pin) as RSAKey)
            .algorithm(JWSAlgorithm.RS256)
            .build()
    }

    fun findJWK(): JWK {
        return jwk
    }
}
