package com.example.hack_for_change_backend.controller

import com.example.hack_for_change_backend.model.User
import com.example.hack_for_change_backend.repository.UserRepo
import com.example.hack_for_change_backend.service.UserService
import org.apache.tomcat.jni.User.username
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException


@RestController
@CrossOrigin(origins = ["http://localhost:3000"])
@RequestMapping("/users")
class UserController(private val userService: UserService) {
    private final val bCryptPasswordEncoder = BCryptPasswordEncoder()

    val userRepo: UserRepo? = null
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
//
//    @PostMapping("/login/login")
//    fun loginUser(@RequestParam email: String, @RequestParam password: String): User{
//        return try{
//            userService.userLogin(email, password)
//        }catch (e: Exception){
//            throw throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message)
//        }
//    }

    @PatchMapping("/users/patch/firstName")
    fun patchFirstName(@RequestBody first_name: String, @RequestParam email: String): ResponseEntity<User> {
        val user: User? = userService.findUserByEmail(email)
        if (user != null) {
            user.firstName = first_name
        }
        return ResponseEntity.ok().body(user)
    }

    @PatchMapping("/users/patch/lastName")
    fun patchLastName(@RequestBody lastName: String, @RequestParam email: String): ResponseEntity<User> {
        val user: User? = userService.findUserByEmail(email)
        if (user != null) {
            user.lastName = lastName
        }
        return ResponseEntity.ok().body(user)
    }

    @PatchMapping("/users/patch/password")
    fun patchPassword(@RequestBody password: String, @RequestParam email: String): ResponseEntity<User> {
        val user: User? = userService.findUserByEmail(email)
        if (user != null) {
            user.password = bCryptPasswordEncoder.encode(password)
        }
        return ResponseEntity.ok().body(user)
    }

    @PatchMapping("/users/patch/email")
    fun patchEmail(@RequestParam email: String): ResponseEntity<User> {
        val user: User? = userService.findUserByEmail(email)
        if (user != null) {
            user.email = email
        }
        return ResponseEntity.ok().body(user)
    }

    @PutMapping("/updateUser/{id}")
    fun userUpdate(@PathVariable("id") userId: Long, @RequestBody user: User): ResponseEntity<User> {
        return try {
            userService.updateUser(userId, user)
        }catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message)
        }
    }

    @DeleteMapping("/deleteUser/{id}")
    fun deleteUser(@PathVariable("id") userId: Long): ResponseEntity<HttpStatus> {
        return try {
            userService.deleteUser(userId)
            ResponseEntity.ok().body(HttpStatus.OK)
        } catch (e: NoSuchElementException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message)
        }
    }

}