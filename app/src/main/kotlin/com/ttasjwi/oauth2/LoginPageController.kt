package com.ttasjwi.oauth2

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class LoginPageController {

    @GetMapping("/login")
    fun loginPage(): String {
        return "로그인 페이지 입니다."
    }
}
