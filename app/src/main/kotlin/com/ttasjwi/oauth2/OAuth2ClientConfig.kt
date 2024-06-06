package com.ttasjwi.oauth2

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.oauth2.client.registration.ClientRegistration
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.security.oauth2.client.registration.ClientRegistrations
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository

@Configuration
class OAuth2ClientConfig {

    @Bean
    fun clientRegistrationRepository(): ClientRegistrationRepository {
        return InMemoryClientRegistrationRepository(keycloakClientRegistration())
    }

    private fun keycloakClientRegistration(): ClientRegistration {
        return ClientRegistrations.fromIssuerLocation("http://localhost:8080/realms/oauth2")
            .registrationId("keycloak")
            .clientId("oauth2-client-app")
            .clientSecret("1tIeERcVJnWNmVZIEFA7Ao5YkTIbx83w")
            .scope("openid", "profile", "email")
            .build()
    }

//    private fun keycloakClientRegistration(): ClientRegistration {
//        return ClientRegistration
//            .withRegistrationId("keycloak")
//
//            // 클라이언트 설정
//            .clientId("oauth2-client-app")
//            .clientSecret("1tIeERcVJnWNmVZIEFA7Ao5YkTIbx83w")
//            .clientName("oauth2-client-app")
//            .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
//            .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
//            .scope("openid", "profile", "email")
//            .redirectUri("{baseUrl}/{action}/oauth2/code/{registrationId}")
//
//            // 공급자 설정
//            .authorizationUri("http://localhost:8080/realms/oauth2/protocol/openid-connect/auth")
//            .tokenUri("http://localhost:8080/realms/oauth2/protocol/openid-connect/token")
//            .jwkSetUri("http://localhost:8080/realms/oauth2/protocol/openid-connect/certs")
//            .userInfoUri("http://localhost:8080/realms/oauth2/protocol/openid-connect/userinfo")
//            .userNameAttributeName("preferred_username")
//            .build()
//    }

}
