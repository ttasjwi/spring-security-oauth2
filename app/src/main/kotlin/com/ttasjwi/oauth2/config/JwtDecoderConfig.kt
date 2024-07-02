package com.ttasjwi.oauth2.config

import com.nimbusds.jose.jwk.JWK
import com.nimbusds.jose.jwk.OctetSequenceKey
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.oauth2.jose.jws.MacAlgorithm
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder

@Configuration
class JwtDecoderConfig {

    @Bean
    @ConditionalOnProperty(
        prefix = "spring.security.oauth2.resourceserver.jwt",
        name = ["jws-algorithms"],
        havingValue = "HS256",
        matchIfMissing = false
    )
    fun jwtDecoderBySecretKeyValue(jwk: JWK, properties: OAuth2ResourceServerProperties): JwtDecoder {
        return NimbusJwtDecoder.withSecretKey((jwk as OctetSequenceKey).toSecretKey())
            .macAlgorithm(MacAlgorithm.from(properties.jwt.jwsAlgorithms[0]))
            .build()
    }
}
