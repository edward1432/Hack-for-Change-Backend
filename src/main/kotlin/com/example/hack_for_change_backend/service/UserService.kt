package com.example.hack_for_change_backend.service

import com.example.hack_for_change_backend.model.Enjoyer
import com.example.hack_for_change_backend.repository.UserRepo
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class UserService(val userRepo: UserRepo) {

    fun findUserById(id: Long): Enjoyer {
        return userRepo.findById(id).orElseThrow {
            NoSuchElementException("[USER] $id NOT FOUND")
        }
    }

    fun findAll(): List<Enjoyer> {
        return userRepo.findAll()
    }

    fun createUser(enjoyer: Enjoyer): ResponseEntity<Enjoyer>{
        userRepo.save(enjoyer)
        return ResponseEntity.ok(enjoyer)
    }


    fun updateUser(userId: Long, enjoyerDetails: Enjoyer): ResponseEntity<Enjoyer> {
        try {
            val user = findUserById(userId)
            user.userName = enjoyerDetails.userName
            user.organisation = enjoyerDetails.organisation
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
