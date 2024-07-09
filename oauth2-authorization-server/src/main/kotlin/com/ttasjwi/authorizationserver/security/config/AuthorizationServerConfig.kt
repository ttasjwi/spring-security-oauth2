package com.ttasjwi.authorizationserver.security.config

import com.nimbusds.jose.jwk.JWKSet
import com.nimbusds.jose.jwk.RSAKey
import com.nimbusds.jose.jwk.source.ImmutableJWKSet
import com.nimbusds.jose.jwk.source.JWKSource
import com.nimbusds.jose.proc.SecurityContext
import com.ttasjwi.oauth2.support.logging.getLogger
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.oauth2.core.AuthorizationGrantType
import org.springframework.security.oauth2.core.ClientAuthenticationMethod
import org.springframework.security.oauth2.core.oidc.OidcScopes
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.util.*

@Configuration
class AuthorizationServerConfig {

    companion object {
        val log = getLogger(AuthorizationServerConfig::class.java)
    }

    /**
     * 인가서버 IssuerUri 등 여러가지 설정정보 관리
     */
    @Bean
    fun authorizationServerSettings(): AuthorizationServerSettings {
        return AuthorizationServerSettings.builder().issuer("http://localhost:9000").build()
    }

    /**
     * OAuth2 클라이언트 정보 저장 및 관리
     */
    @Bean
    fun registeredClientRepository(): RegisteredClientRepository {
        val registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
            .clientId("oauth2-client-app")
            .clientSecret("{noop}secret")
            .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
            .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST)
            .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
            .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
            .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
            .redirectUri("http://127.0.0.1:8081")
            .scope(OidcScopes.OPENID)
            .scope("read")
            .scope("write")
            .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
            .build()

        return InMemoryRegisteredClientRepository(registeredClient)
    }

    @Bean
    fun jwtDecoder(jwkSource: JWKSource<SecurityContext>): JwtDecoder {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource)
    }

    @Bean
    fun jwkSource(): JWKSource<SecurityContext> {
        val rsaKey = getRsaKey()
        val jwkSet = JWKSet(rsaKey)
        return ImmutableJWKSet(jwkSet)
    }

    private fun getRsaKey(): RSAKey {
        val keyPair = generateRsaKeyPair()
        return RSAKey
            .Builder(keyPair.public as RSAPublicKey)
            .privateKey(keyPair.private as RSAPrivateKey)
            .keyID(UUID.randomUUID().toString())
            .build()
    }

    private fun generateRsaKeyPair(): KeyPair {
        val keyPair: KeyPair
        try {
            val keyPairGenerator = KeyPairGenerator.getInstance("RSA")
            keyPairGenerator.initialize(2048)
            keyPair = keyPairGenerator.generateKeyPair()
        } catch(e: Exception) {
            log.error(e) { "인가서버 jwk 설정 실패 - RSA 키 생성 과정에서 오류가 발생했습니다." }
            throw IllegalStateException(e)
        }
        return keyPair
    }

}