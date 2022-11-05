package com.example.hack_for_change_backend.email

import lombok.AllArgsConstructor
import org.slf4j.LoggerFactory
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import javax.mail.MessagingException
import javax.mail.internet.MimeMessage


@Service
@AllArgsConstructor
class EmailService : EmailSender {
    private val mailSender: JavaMailSender? = null

    @Async
    override fun send(to: String?, email: String?) {
        try {
            val mimeMessage: MimeMessage = mailSender!!.createMimeMessage()
            val helper = MimeMessageHelper(mimeMessage, "utf-8")
            helper.setText(email, true)
            helper.setTo(to)
            helper.setSubject("Confirm your email")
            helper.setFrom("yourmum@yourmum.com")
            mailSender.send(mimeMessage)
        } catch (e: MessagingException) {
            LOGGER.error("[EMAIL] FAILED TO SEND", e)
            throw IllegalStateException("[EMAIL] FAILED TO SEND")
        }
    }

    companion object {
        private val LOGGER = LoggerFactory
            .getLogger(EmailService::class.java)
    }
}