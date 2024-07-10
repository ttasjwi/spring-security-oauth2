package com.ttasjwi.authorizationserver.controller

import org.springframework.security.oauth2.server.authorization.client.RegisteredClient
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class RegisteredClientController(
    private val registeredClientRepository: RegisteredClientRepository
) {

    @GetMapping("/registeredClients")
    fun list(): List<RegisteredClient> {
        val client1 = registeredClientRepository.findByClientId("oauth2-client-app1")!!
        val client2 = registeredClientRepository.findByClientId("oauth2-client-app2")!!
        val client3 = registeredClientRepository.findByClientId("oauth2-client-app3")!!

        return listOf(client1, client2, client3)
    }
}
