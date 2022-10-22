package com.example.hack_for_change_backend.service


import com.example.hack_for_change_backend.model.User
import com.example.hack_for_change_backend.model.VerificationToken
import com.example.hack_for_change_backend.repository.UserRepo
import com.example.hack_for_change_backend.repository.VerificationTokenRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.server.ResponseStatusException
import java.util.*
import java.util.stream.Collectors
import javax.validation.Valid
import kotlin.math.sign


@Component
class UserDataService @Autowired
constructor(private val userRepository: UserRepo) : UserDetailsService {
    @Autowired
    lateinit var tokenRepository: VerificationTokenRepository

    @Autowired
    lateinit var encoder: PasswordEncoder

    companion object {
        val TOKEN_VALID: String = "valid"
        val TOKEN_INVALID: String = "invalid"
        val TOKEN_EXPIRED: String = "expired"
    }
    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(email: String): UserDetails {
        val user = userRepository.findByEmail(email)
        val authorities: List<GrantedAuthority> = user.roles!!.stream().map { role -> SimpleGrantedAuthority(role.roleName) }
            .collect(
            Collectors.toList<GrantedAuthority>())

        return org.springframework.security.core.userdetails.User
            .withUsername(email)
            .password(user.password)
            .authorities(authorities)
            .accountExpired(false)
            .accountLocked(false)
            .credentialsExpired(false)
            .disabled(false)
            .build()
    }
     fun createVerificationTokenForUser(token: String, user: User) {
        tokenRepository.save(VerificationToken(token, user))
    }




        fun validateVerificationToken(token: String): String {
        val verificationToken: Optional<VerificationToken> = tokenRepository.findByToken(token)

        if (verificationToken.isPresent) {
            val user: User = verificationToken.get().user
            val cal: Calendar = Calendar.getInstance()
            if ((verificationToken.get().expiryDate.time - cal.time.time) <= 0) {
                tokenRepository.delete(verificationToken.get())
                return TOKEN_EXPIRED
            }

            tokenRepository.delete(verificationToken.get())
            userRepository.save(user)
            return TOKEN_VALID
        } else {
            return TOKEN_INVALID
        }
    }
}
