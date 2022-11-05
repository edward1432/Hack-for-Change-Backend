package com.example.hack_for_change_backend.registration.token

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.*


@Repository
@Transactional(readOnly = true)
interface ConfirmationTokenRepository : JpaRepository<ConfirmationToken?, Long?> {
    fun findByToken(token: String?): Optional<ConfirmationToken?>?

    @Transactional
    @Modifying
    @Query(
        "UPDATE ConfirmationToken c " +
                "SET c.confirmedAt = ?2 " +
                "WHERE c.token = ?1"
    )
    fun updateConfirmedAt(
        token: String?,
        confirmedAt: LocalDateTime?
    ): Int
}