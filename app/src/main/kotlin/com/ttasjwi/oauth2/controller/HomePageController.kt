package com.ttasjwi.oauth2.controller

import org.springframework.security.oauth2.client.OAuth2AuthorizedClient
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class HomePageController(
    private val oAuth2AuthorizedClientService: OAuth2AuthorizedClientService
) {

    @GetMapping("/home")
    fun home(model: Model, oauth2AuthenticationToken: OAuth2AuthenticationToken): String {
        val oauth2AuthorizedClient = oAuth2AuthorizedClientService.loadAuthorizedClient<OAuth2AuthorizedClient>(
            "keycloak", oauth2AuthenticationToken.name
        )
        model.addAttribute("oauth2AuthenticationToken", oauth2AuthenticationToken)
        model.addAttribute("accessToken", oauth2AuthorizedClient.accessToken.tokenValue)
        model.addAttribute("refreshToken", oauth2AuthorizedClient.refreshToken!!.tokenValue)
        return "home"
    }

}
