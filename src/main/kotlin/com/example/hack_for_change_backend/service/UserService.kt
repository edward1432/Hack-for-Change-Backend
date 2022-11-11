package com.example.hack_for_change_backend.service

import com.example.hack_for_change_backend.model.User
import com.example.hack_for_change_backend.model.enums.UserRoles
import com.example.hack_for_change_backend.registration.token.ConfirmationToken
import com.example.hack_for_change_backend.registration.token.ConfirmationTokenService
import com.example.hack_for_change_backend.repository.UserRepo
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*
import kotlin.NoSuchElementException

@Service
class UserService(
    val userRepo: UserRepo,
    val confirmationTokenService: ConfirmationTokenService,
    val organisationService: OrganisationService
) : UserDetailsService {

    private val notFoundMsg = "User with email %s not found"
    private final val bCryptPasswordEncoder = BCryptPasswordEncoder()

    fun findUserById(id: Long): User {
        return userRepo.findById(id).orElseThrow {
            NoSuchElementException("[USER] $id NOT FOUND")
        }
    }

    fun checkUserEmail(email: String): Boolean = findAll().any { it.email == email }

    fun findAll(): List<User> {
        return userRepo.findAll()
    }

    fun login(email: String, password: String): User {
        val user = userRepo.findByEmail(email)
        return if (bCryptPasswordEncoder.matches(password, user!!.password)) {
            user
        } else {
            throw Exception("Incorrect password")
        }
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

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(email: String): UserDetails? =
        userRepo.findByEmail(email) ?: throw UsernameNotFoundException(String.format(notFoundMsg, email))

    fun signUpUser(user: User, organisationId: Long): String {
        try {
            val org = organisationService.findOrganisationById(organisationId)

            if (checkUserEmail(user.email)) {
                throw java.lang.IllegalArgumentException("Email ${user.email} already registered")
            }

            user.run {
                password = bCryptPasswordEncoder.encode(user.password)
                role = UserRoles.USER
                organisation = org
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

            return token
        } catch (e: NoSuchElementException) {
            throw NoSuchElementException(e.message)
        }
    }

    fun enableAppUser(email: String): Int = userRepo.enableUser(email)
}
