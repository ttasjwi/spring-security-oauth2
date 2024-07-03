package com.ttasjwi.oauth2.security.signature

import com.nimbusds.jose.JWSAlgorithm
import com.nimbusds.jose.JWSHeader
import com.nimbusds.jose.JWSSigner
import com.nimbusds.jose.jwk.JWK
import com.nimbusds.jwt.JWTClaimsSet
import com.nimbusds.jwt.SignedJWT
import org.springframework.security.core.userdetails.User
import java.util.*

abstract class TokenSigner(
    private val jwkRepository: JWKRepository,
) {

    fun signToken(user: User): String {
        val jwk = jwkRepository.findJWK()

        val header = JWSHeader.Builder(jwk.algorithm as JWSAlgorithm).keyID(jwk.keyID).build()
        val authority = user.authorities.map { it.authority }.toList()

        val claimSet = JWTClaimsSet.Builder()
            .subject("user")
            .issuer("http://localhost:8081")
            .claim("username", user.username)
            .claim("authority", authority)
            .expirationTime(Date(Date().time + 60 * 100 * 5))
            .build()

        val signedJWT = SignedJWT(header, claimSet)
        signedJWT.sign(getJWSSigner(jwk))
        return signedJWT.serialize()
    }

    protected abstract fun getJWSSigner(jwk: JWK): JWSSigner
}
