package com.example.hack_for_change_backend.service

import com.example.hack_for_change_backend.model.User
import com.example.hack_for_change_backend.repository.UserRepo
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class UserService(val userRepo: UserRepo) {

    fun findUserById(id: Long): User {
        return userRepo.findById(id).orElseThrow {
            NoSuchElementException("[USER] $id NOT FOUND")
        }
    }

    fun findAll(): List<User> {
        return userRepo.findAll()
    }

    fun createUser(user: User): ResponseEntity<User>{
        userRepo.save(user)
        return ResponseEntity.ok(user)
    }


    fun updateUser(userId: Long, userDetails: User): ResponseEntity<User> {
        try {
            val user = findUserById(userId)
            user.userName = userDetails.userName
            user.organisation = userDetails.organisation
            return ResponseEntity.ok(user)
        } catch (e: NoSuchElementException) {
            throw e
        }
    }

    fun deleteUser(userId: Long): ResponseEntity<HttpStatus>{
        try{
            userRepo.delete(findUserById(userId))
            return ResponseEntity.ok(HttpStatus.OK)
        }catch(e: NoSuchElementException){
            throw e
        }
    }
}
