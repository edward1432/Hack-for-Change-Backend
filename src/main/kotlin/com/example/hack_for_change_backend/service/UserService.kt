package com.example.hack_for_change_backend.service

import com.example.hack_for_change_backend.email.EmailService
import com.example.hack_for_change_backend.model.User
import com.example.hack_for_change_backend.repository.UserRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.server.ResponseStatusException
import java.lang.IllegalArgumentException
import javax.validation.Valid

@Service
class UserService(val userRepo: UserRepo) {

    @Autowired
    lateinit var emailService: EmailService
    fun findUserById(id: Long): User {
        return userRepo.findById(id).orElseThrow {
            NoSuchElementException("[USER] $id NOT FOUND")
        }
    }

    fun findAll(): List<User> {
        return userRepo.findAll()
    }
    private fun emailExists(email: String): Boolean {
        return userRepo.emailIsPresent(email).isPresent
    }
    fun createUser(user: User): ResponseEntity<User>{
        val signUpUser: User = userRepo.findByEmail(user.email!!)
        if (!emailExists(user.email)) {
            user.password = PasswordEncrypt.encodePassword(user.password)
        }else{
            throw IllegalArgumentException("[EMAIL] ${user.email} ALREADY IN USE")
        }
        val registeredUser = userRepo.save(user)
        emailService.sendRegistrationConfirmationEmail(registeredUser)
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
