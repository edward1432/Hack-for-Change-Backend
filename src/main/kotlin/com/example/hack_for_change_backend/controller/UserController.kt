package com.example.hack_for_change_backend.controller

import com.example.hack_for_change_backend.model.Event
import com.example.hack_for_change_backend.model.User
import com.example.hack_for_change_backend.service.EventService
import com.example.hack_for_change_backend.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/users")
class UserController(private val userService: UserService) {

    @GetMapping("/findAll")
    fun getAllUsers(): ResponseEntity<List<User>> = ResponseEntity.ok(userService.findAll())

    @GetMapping("/findById/{id}")
    fun findUserById(@PathVariable("id") userId: Long): ResponseEntity<User> {
        return try {
            ResponseEntity.ok(userService.findUserById(userId))
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message)
        }
    }
    @PostMapping("/addUser")
    fun addUser(@RequestBody user: User): ResponseEntity<User> {
        return try {
            userService.createUser(user)
        }catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message)
        }
    }

    @PutMapping("/updateUser/{id}")
    fun updateUser(@PathVariable("id") userId: Long, @RequestBody user: User): ResponseEntity<User> {
        return try {
            userService.updateUser(userId, user)
        }catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message)
        }
    }

}