package com.example.hack_for_change_backend.controller

import com.example.hack_for_change_backend.model.Enjoyer
import com.example.hack_for_change_backend.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/users")
class UserController(private val userService: UserService) {

    @GetMapping("/findAll")
    fun getAllUsers(): ResponseEntity<List<Enjoyer>> = ResponseEntity.ok(userService.findAll())

    @GetMapping("/findById/{id}")
    fun findUserById(@PathVariable("id") userId: Long): ResponseEntity<Enjoyer> {
        return try {
            ResponseEntity.ok(userService.findUserById(userId))
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message)
        }
    }
    @PostMapping("/addUser")
    fun addUser(@RequestBody enjoyer: Enjoyer): ResponseEntity<Enjoyer> {
        return try {
            userService.createUser(enjoyer)
        }catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message)
        }
    }

    @PutMapping("/updateUser/{id}")
    fun updateUser(@PathVariable("id") userId: Long, @RequestBody enjoyer: Enjoyer): ResponseEntity<Enjoyer> {
        return try {
            userService.updateUser(userId, enjoyer)
        }catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message)
        }
    }

    @DeleteMapping("/userOrganisation" + "/{id}")
    fun deleteUser(@PathVariable("id") userId: Long): ResponseEntity<HttpStatus> {
        return try {
            userService.deleteUser(userId)
            ResponseEntity.ok().body(HttpStatus.OK)
        } catch (e: NoSuchElementException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message)
        }
    }

}