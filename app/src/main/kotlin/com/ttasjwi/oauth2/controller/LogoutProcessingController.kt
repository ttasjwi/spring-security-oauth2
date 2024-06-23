package com.ttasjwi.oauth2.controller

import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.logout.CompositeLogoutHandler
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class LogoutProcessingController {

    private val logoutHandler = CompositeLogoutHandler(
        SecurityContextLogoutHandler(),
        CookieClearingLogoutHandler("JSESSIONID")
    )

    @GetMapping("/logout")
    fun logout(request: HttpServletRequest, response: HttpServletResponse, authentication: Authentication): String {
        logoutHandler.logout(request, response, authentication)
        return "redirect:/login"
    }
}
