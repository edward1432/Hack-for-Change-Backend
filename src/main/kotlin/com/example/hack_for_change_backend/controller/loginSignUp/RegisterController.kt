package com.example.hack_for_change_backend.controller.loginSignUp

import com.example.hack_for_change_backend.model.User
import com.example.hack_for_change_backend.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException


@RestController
@CrossOrigin(origins = ["http://localhost:3000"])
@RequestMapping("/users")
class RegisterController(private val userService: UserService) {
    @PostMapping("/register")
    fun addUser(@RequestBody user: User): ResponseEntity<User> {
        return try {
            userService.createUser(user)
        }catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message)
        }
    }
}