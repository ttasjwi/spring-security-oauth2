package com.ttasjwi.oauth2.security.config

import com.nimbusds.jose.JWSVerifier
import com.nimbusds.jose.crypto.RSASSAVerifier
import com.nimbusds.jose.jwk.RSAKey
import com.ttasjwi.oauth2.security.signature.JWKRepository
import com.ttasjwi.oauth2.security.signature.RSATokenSigner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SignatureConfig {

    @Bean
    fun tokenSigner(jwkRepository: JWKRepository): RSATokenSigner {
        return RSATokenSigner(jwkRepository)
    }

    @Bean
    fun jwkRepository(): JWKRepository {
        return JWKRepository(
            jksPath = "C:\\Users\\ttasjwi\\projects\\spring\\spring-security-oauth2\\app\\src\\main\\resources\\certs\\apiKey.jks",
            alias = "apiKey",
            pin = "pass1234".toCharArray()
        )
    }
}
