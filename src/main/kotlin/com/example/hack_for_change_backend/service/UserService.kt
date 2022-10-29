package com.example.hack_for_change_backend.service

import com.example.hack_for_change_backend.model.Role
import com.example.hack_for_change_backend.model.User
import com.example.hack_for_change_backend.model.enums.UserRoles
import com.example.hack_for_change_backend.repository.UserRepo
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(val userRepo: UserRepo, val passwordEncoder: PasswordEncoder) {

    fun findUserById(id: Long): User {
        return userRepo.findById(id).orElseThrow {
            NoSuchElementException("[USER] $id NOT FOUND")
        }
    }

    fun findAll(): List<User> {
        return userRepo.findAll()
    }

    fun createUser(user: User): ResponseEntity<User> {
        user.run {
            password = passwordEncoder.encode(user.password)
            role = UserRoles.USER
        }
        userRepo.save(user)
        return ResponseEntity.ok(user)
    }


    fun updateUser(userId: Long, userDetails: User): ResponseEntity<User> {
        try {
            val user = findUserById(userId)
            user.email = userDetails.email
            user.organisation = userDetails.organisation
            return ResponseEntity.ok(user)
        } catch (e: NoSuchElementException) {
            throw NoSuchElementException(e.message)
        }
    }

    fun deleteUser(userId: Long): ResponseEntity<HttpStatus>{
        try{
            userRepo.delete(findUserById(userId))
            return ResponseEntity.ok(HttpStatus.OK)
        }catch(e: NoSuchElementException){
            throw NoSuchElementException(e.message)
        }
    }
}
