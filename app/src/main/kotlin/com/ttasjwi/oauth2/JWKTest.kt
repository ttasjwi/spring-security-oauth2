package com.ttasjwi.oauth2

import com.nimbusds.jose.JWSAlgorithm
import com.nimbusds.jose.jwk.JWK
import com.nimbusds.jose.jwk.JWKMatcher
import com.nimbusds.jose.jwk.JWKSelector
import com.nimbusds.jose.jwk.JWKSet
import com.nimbusds.jose.jwk.KeyOperation
import com.nimbusds.jose.jwk.KeyType
import com.nimbusds.jose.jwk.KeyUse
import com.nimbusds.jose.jwk.OctetSequenceKey
import com.nimbusds.jose.jwk.RSAKey
import com.nimbusds.jose.jwk.gen.OctetSequenceKeyGenerator
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator
import com.nimbusds.jose.jwk.source.JWKSource
import com.nimbusds.jose.proc.SecurityContext
import java.security.KeyPairGenerator
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.util.*
import javax.crypto.spec.SecretKeySpec


class JWKTest {

    companion object {

        fun jwkTest() {
            // 비대칭키 JWK
            val rsaKeyPairGenerator = KeyPairGenerator.getInstance("RSA")
            rsaKeyPairGenerator.initialize(2048)

            val keyPair = rsaKeyPairGenerator.generateKeyPair()
            val publicKey = keyPair.public as RSAPublicKey
            val privateKey = keyPair.private as RSAPrivateKey

            val rsaKey1 = RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyUse(KeyUse.SIGNATURE)
                .algorithm(JWSAlgorithm.RS256)
                .keyID("rsa-key-1")
                .build()

            val rsaKey2 = RSAKeyGenerator(2048)
                .keyID("rsa-key2")
                .keyUse(KeyUse.SIGNATURE)
                .keyOperations(setOf(KeyOperation.SIGN))
                .algorithm(JWSAlgorithm.RS512)
                .generate()

            // 대칭키 JWK
            val secretKey = SecretKeySpec(
                Base64.getDecoder().decode("bCzY/M48bbkwBEWjmNSIEPfwApcvXOnkCxORBEbPr+4="), "AES"
            )

            val octetSequenceKey1 = OctetSequenceKey.Builder(secretKey)
                .keyID("secret-kid1")
                .keyUse(KeyUse.SIGNATURE)
                .keyOperations(setOf(KeyOperation.SIGN))
                .algorithm(JWSAlgorithm.HS256)
                .build()

            val octetSequenceKey2 = OctetSequenceKeyGenerator(256)
                .keyID("secret-kid2")
                .keyUse(KeyUse.SIGNATURE)
                .keyOperations(setOf(KeyOperation.SIGN))
                .algorithm(JWSAlgorithm.HS384)
                .generate()

            jwtSet(
//                kid = rsaKey1.keyID, alg = rsaKey1.algorithm as JWSAlgorithm, type = rsaKey1.keyType,
                kid = rsaKey2.keyID, alg = rsaKey2.algorithm as JWSAlgorithm, type = rsaKey2.keyType,
//                kid = octetSequenceKey1.keyID, alg = octetSequenceKey1.algorithm as JWSAlgorithm, type = octetSequenceKey1.keyType,
//                kid = octetSequenceKey2.keyID, alg = octetSequenceKey2.algorithm as JWSAlgorithm, type = octetSequenceKey2.keyType,
                rsaKey1,
                rsaKey2,
                octetSequenceKey1,
                octetSequenceKey2
            )
        }

        private fun jwtSet(kid: String, alg: JWSAlgorithm, type: KeyType, vararg jwk: JWK) {
            val jwkSet = JWKSet(mutableListOf(*jwk))
            val jwkSource = JWKSource<SecurityContext> { jwkSelector, _ ->
                jwkSelector.select(jwkSet)
            }

            val jwkMatcher = JWKMatcher.Builder()
                .keyTypes(type)
                .keyID(kid)
                .keyUses(KeyUse.SIGNATURE)
                .algorithms(alg)
                .build()

            val jwkSelector = JWKSelector(jwkMatcher)
            val jwks = jwkSource.get(jwkSelector, null)

            println("jwks= $jwks")
            if (jwks.isNotEmpty()) {
                val findJwk = jwks[0]

                val keyType = findJwk.keyType
                println("keyType = $keyType")

                val keyId = findJwk.keyID
                println("keyID = $keyId")

                val algorithm = findJwk.algorithm
                println("algorithm  = $algorithm")
            }
        }
    }
}
