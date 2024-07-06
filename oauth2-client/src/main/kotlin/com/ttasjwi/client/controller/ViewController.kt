package com.ttasjwi.client.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class ViewController {

    @GetMapping("/")
    fun index() = "index"

    @GetMapping("/home")
    fun home() = "home"
}
