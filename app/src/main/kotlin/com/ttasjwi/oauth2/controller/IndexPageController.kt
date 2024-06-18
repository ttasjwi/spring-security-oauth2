package com.ttasjwi.oauth2.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class IndexPageController {

    @GetMapping("/")
    fun indexPage(): String {
        return "index"
    }

}
