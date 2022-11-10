package com.example.hack_for_change_backend.controller

import com.example.hack_for_change_backend.model.User
import com.example.hack_for_change_backend.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import kotlin.NoSuchElementException


@RestController
@CrossOrigin(origins = ["http://localhost:3000"])
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
//    @PostMapping("/addUser")
//    fun addUser(@RequestBody user: User): ResponseEntity<User> {
//        return try {
//            userService.createUser(user)
//        }catch (e: Exception) {
//            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message)
//        }
//    }

//    @PostMapping("/login/login")
//    fun loginUser(@RequestParam email: String, @RequestParam password: String): User{
//        return try{
//            userService.userLogin(email, password)
//        }catch (e: Exception){
//            throw throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message)
//        }
//    }

    @GetMapping("/loginUser")
    fun loginUser(@RequestParam email: String, @RequestParam password: String): ResponseEntity<User> {
        return try {
            ResponseEntity.ok(userService.login(email, password))
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message)
        }
    }

    @PutMapping("/updateUser/{id}")
    fun userUpdate(@PathVariable("id") userId: Long, @RequestBody user: User): ResponseEntity<User> {
        return try {
            userService.updateUser(userId, user)
        }catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message)
        }
    }

    @DeleteMapping("/deleteUser" + "/{id}")
    fun deleteUser(@PathVariable("id") userId: Long): ResponseEntity<HttpStatus> {
        return try {
            userService.deleteUser(userId)
            ResponseEntity.ok().body(HttpStatus.OK)
        } catch (e: NoSuchElementException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message)
        }
    }

}