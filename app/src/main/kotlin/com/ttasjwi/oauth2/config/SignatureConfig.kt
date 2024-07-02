package com.ttasjwi.oauth2.config

import com.nimbusds.jose.JWSAlgorithm
import com.nimbusds.jose.jwk.JWK
import com.nimbusds.jose.jwk.gen.OctetSequenceKeyGenerator
import com.ttasjwi.oauth2.security.signature.JWKRepository
import com.ttasjwi.oauth2.security.signature.MacTokenSigner
import com.ttasjwi.oauth2.security.signature.TokenSigner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SignatureConfig {

    @Bean
    fun tokenSigner(jwkRepository: JWKRepository): TokenSigner {
        return MacTokenSigner(jwkRepository)
    }

    @Bean
    fun jwkRepository(jwk: JWK): JWKRepository {
        return JWKRepository(jwk)
    }

    @Bean
    fun jwk(): JWK {
        return OctetSequenceKeyGenerator(256).keyID("macKey").algorithm(JWSAlgorithm.HS256).generate()
    }

}
