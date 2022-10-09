package com.example.hack_for_change_backend.controller

import com.example.hack_for_change_backend.model.Employee
import com.example.hack_for_change_backend.service.UserService
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.util.*
import kotlin.NoSuchElementException


@RestController
@CrossOrigin(origins = ["http://localhost:3000"])
@RequestMapping("/users")
class UserController(private val userService: UserService) {

    @GetMapping("/findAll")
    fun getAllUsers(): ResponseEntity<List<Employee>> = ResponseEntity.ok(userService.findAll())

    @GetMapping("/findById/{id}")
    fun findUserById(@PathVariable("id") userId: Long): ResponseEntity<Employee> {
        return try {
            ResponseEntity.ok(userService.findUserById(userId))
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message)
        }
    }
    @PostMapping("/addUser")
    fun addUser(@RequestBody employee: Employee): ResponseEntity<Employee> {
        return try {
            userService.createUser(employee)
        }catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message)
        }
    }

    @PutMapping("/updateUser/{id}")
    fun updateUser(@PathVariable("id") userId: Long, @RequestBody employee: Employee): ResponseEntity<Employee> {
        return try {
            userService.updateUser(userId, employee)
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