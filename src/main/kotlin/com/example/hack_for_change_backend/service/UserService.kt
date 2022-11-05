package com.example.hack_for_change_backend.service

import com.example.hack_for_change_backend.model.User
import com.example.hack_for_change_backend.model.enums.UserRoles
import com.example.hack_for_change_backend.registration.token.ConfirmationToken
import com.example.hack_for_change_backend.registration.token.ConfirmationTokenService
import com.example.hack_for_change_backend.repository.UserRepo
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

@Service
class UserService(val userRepo: UserRepo,
                  val passwordEncoder: PasswordEncoder,
                  val bCryptPasswordEncoder: BCryptPasswordEncoder,
                  val confirmationTokenService: ConfirmationTokenService
                  ) : UserDetailsService {

    private val notFoundMsg = "User with email %s not found"

    fun findUserById(id: Long): User {
        return userRepo.findById(id).orElseThrow {
            NoSuchElementException("[USER] $id NOT FOUND")
        }
    }

    fun checkUserEmail(email: String): Boolean = findAll().any { it.email == email }

    fun findAll(): List<User> {
        return userRepo.findAll()
    }

//    fun userLogin(email: String, password: String): User {

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

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(email: String): UserDetails? =
        userRepo.findByEmail(email) ?: throw UsernameNotFoundException(String.format(notFoundMsg, email))

    //    }
//        }
//            throw NoSuchElementException("[EMAIL] $email NOT FOUND")
//        } else {
//            return userRepo.findByEmail(email)
//        if (passwordEncoder.matches(password, userRepo.findByEmail(email).password)) {
    fun createUser(user: User): ResponseEntity<User> {
        if (!checkUserEmail(user.email)) {
            user.run {
                password = passwordEncoder.encode(user.password)
                role = UserRoles.USER
            }
            userRepo.save(user)
            return ResponseEntity.ok(user)

        } else throw IllegalArgumentException("Email ${user.email} already registered")
    }

    fun signUpUser(user: User): String? {

        if (checkUserEmail(user.email)) {
            throw java.lang.IllegalArgumentException("Email ${user.email} already registered")
        }

        user.run {
            password = bCryptPasswordEncoder.encode(user.password)
            role = UserRoles.USER
        }
        userRepo.save(user)

        val token = UUID.randomUUID().toString()
        val confirmationToken = ConfirmationToken(
            token,
            LocalDateTime.now(),
            LocalDateTime.now().plusMinutes(15),
            user
        )
        confirmationTokenService.saveConfirmationToken(
            confirmationToken
        )


//        TODO: SEND EMAIL
        return token
    }

    fun enableAppUser(email: String): Int = userRepo.enableUser(email)
}
