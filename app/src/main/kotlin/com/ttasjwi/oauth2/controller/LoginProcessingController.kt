package com.ttasjwi.oauth2.controller

import ch.qos.logback.core.model.Model
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class LoginProcessingController {

    @GetMapping("/oauth2Login")
    fun oauth2Login(request: HttpServletRequest, response: HttpServletResponse, model: Model): String {
        return "redirect:/"
    }
}
