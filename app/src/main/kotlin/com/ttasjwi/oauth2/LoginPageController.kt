package com.ttasjwi.oauth2

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class LoginPageController {

    @GetMapping("/login")
    fun loginPage(): String {
        return "login"
    }
}
