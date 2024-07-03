package com.ttasjwi.oauth2.security.config

import com.nimbusds.jose.JWSAlgorithm
import com.nimbusds.jose.JWSVerifier
import com.nimbusds.jose.crypto.RSASSAVerifier
import com.nimbusds.jose.jwk.RSAKey
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator
import com.ttasjwi.oauth2.security.signature.JWKRepository
import com.ttasjwi.oauth2.security.signature.RSATokenSigner
import com.ttasjwi.oauth2.security.signature.TokenSigner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SignatureConfig {

    @Bean
    fun tokenSigner(jwkRepository: JWKRepository): TokenSigner {
        return RSATokenSigner(jwkRepository)
    }

    @Bean
    fun jwsVerifier(jwkRepository: JWKRepository): JWSVerifier {
        return RSASSAVerifier((jwkRepository.findJWK() as RSAKey).toRSAPublicKey())
    }

    @Bean
    fun jwkRepository(): JWKRepository {
        val jwk = RSAKeyGenerator(2048)
            .keyID("rsaKey")
            .algorithm(JWSAlgorithm.RS256)
            .generate()
        return JWKRepository(jwk)
    }
}
