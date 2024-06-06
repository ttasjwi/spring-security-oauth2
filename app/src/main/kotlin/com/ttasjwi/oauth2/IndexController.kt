package com.ttasjwi.oauth2

import com.ttasjwi.oauth2.support.logging.getLogger
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class IndexController(
    private val clientRegistrationRepository: ClientRegistrationRepository
) {

    companion object {
        private val log = getLogger(IndexController::class.java)
    }

    @GetMapping("/")
    fun index(): String {
        val clientRegistration = clientRegistrationRepository.findByRegistrationId("keycloak")

        val clientId = clientRegistration.clientId
        log.info { "clientId = $clientId" }

        val redirectUri = clientRegistration.redirectUri
        log.info { "redirectUri = $redirectUri" }

        return "index"
    }
}
