package com.example.hack_for_change_backend.registration.token

import lombok.AllArgsConstructor
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*


@Service
@AllArgsConstructor
class ConfirmationTokenService {
    private val confirmationTokenRepository: ConfirmationTokenRepository? = null
    fun saveConfirmationToken(token: ConfirmationToken) {
        confirmationTokenRepository!!.save(token)
    }

    fun getToken(token: String?): Optional<ConfirmationToken?>? {
        return confirmationTokenRepository!!.findByToken(token)
    }

    fun setConfirmedAt(token: String?): Int {
        return confirmationTokenRepository!!.updateConfirmedAt(
            token, LocalDateTime.now()
        )
    }
}