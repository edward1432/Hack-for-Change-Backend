package com.example.hack_for_change_backend.service

import com.example.hack_for_change_backend.DTO.UserRegistrationDto
import com.example.hack_for_change_backend.model.User
import org.springframework.security.core.userdetails.UserDetailsService

interface UserDataService : UserDetailsService {
    fun save(registrationDto: UserRegistrationDto?): User?
    val all: List<User?>?
}