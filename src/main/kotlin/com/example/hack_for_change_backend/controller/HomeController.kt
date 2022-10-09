package com.example.hack_for_change_backend.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class HomeController {
    @GetMapping("/login")
    fun login(): String {
        return "login"
    }

    @GetMapping("/")
    fun home(): String {
        return "index"
    }
}