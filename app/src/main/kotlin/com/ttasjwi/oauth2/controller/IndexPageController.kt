package com.ttasjwi.oauth2.controller

import com.ttasjwi.oauth2.model.users.AuthUser
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class IndexPageController {

    @GetMapping("/")
    fun indexPage(model: Model, authentication: Authentication?): String {
        if (authentication == null) {
            return "index"
        }
        val user = authentication.principal as AuthUser
        model.addAttribute("user", user.username)
        model.addAttribute("provider", user.providerUser.provider)

        if (!user.providerUser.isCertificated) {
            return "selfcert"
        }
        return "index"
    }

}
