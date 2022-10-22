package com.example.hack_for_change_backend.repository


import com.example.hack_for_change_backend.model.VerificationToken
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface VerificationTokenRepository : JpaRepository<VerificationToken, Long> {
    fun findByToken(token: String): Optional<VerificationToken>
}