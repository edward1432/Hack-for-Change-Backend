package com.example.hack_for_change_backend.service

import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.bcrypt.BCrypt
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

class PasswordEncrypt : PasswordEncoder {
    @Bean
    override fun encode(rawPassword: CharSequence): String {

        return BCrypt.hashpw(rawPassword.toString(), BCrypt.gensalt(12))
    }

    override fun matches(rawPassword: CharSequence, encodedPassword: String): Boolean {
        return BCrypt.checkpw(rawPassword.toString(), encodedPassword)
    }

    companion object {
        @Bean
        fun encodePassword(password: String): String {
            val passwordEncoder = BCryptPasswordEncoder()
            return passwordEncoder.encode(password)
        }
    }
}