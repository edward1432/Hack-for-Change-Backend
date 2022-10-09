package com.example.hack_for_change_backend.controller

import com.example.hack_for_change_backend.DTO.UserRegistrationDto
import com.example.hack_for_change_backend.service.UserDataService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping


@Controller
@RequestMapping("/registration")
class RegistrationController(private val userService: UserDataService) {
    @ModelAttribute("user")
    fun userRegistrationDto(): UserRegistrationDto {
        return UserRegistrationDto()
    }

    @GetMapping
    fun showRegistrationForm(): String {
        return "registration"
    }

    @PostMapping
    fun registerUserAccount(@ModelAttribute("user") registrationDto: UserRegistrationDto?): String {
        userService.save(registrationDto)
        return "redirect:/registration?success"
    }
}