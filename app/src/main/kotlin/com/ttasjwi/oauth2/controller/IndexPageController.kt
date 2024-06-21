package com.ttasjwi.oauth2.controller

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class IndexPageController {

    @GetMapping("/")
    fun indexPage(model: Model, authentication: OAuth2AuthenticationToken?): String {
        if (authentication !== null) {
            model.addAttribute("user", resolveUsername(authentication))
        }
        return "index"
    }

    private fun resolveUsername(authentication: OAuth2AuthenticationToken): String {
        val oauth2User = authentication.principal
        val attributes = oauth2User.attributes

        val name = when (authentication.authorizedClientRegistrationId) {
            "naver" -> (attributes["response"] as Map<*, *>)["name"]
            "google" -> attributes["sub"]
            "keycloak" -> attributes["preferred_username"]
            else -> throw IllegalStateException()
        } as String
        return name
    }

}
