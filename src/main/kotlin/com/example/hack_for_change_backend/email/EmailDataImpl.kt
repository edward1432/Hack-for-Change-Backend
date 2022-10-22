package com.example.hack_for_change_backend.email

import com.example.hack_for_change_backend.model.User
import com.example.hack_for_change_backend.service.UserDataService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.MailException
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Component
import org.thymeleaf.spring5.SpringTemplateEngine
import org.thymeleaf.context.Context
import org.springframework.core.env.Environment
import java.util.*

@Component
class EmailServiceImpl : EmailService {

    @Value("\${spring.mail.username}")
    lateinit var sender: String

    @Value("\${host.url}")
    lateinit var hostUrl: String

    @Autowired
    lateinit var userDetailsService: UserDataService

    @Autowired
    lateinit var environment: Environment

    @Autowired
    var emailSender: JavaMailSender? = null

    @Autowired
    lateinit var templateEngine: SpringTemplateEngine

    override fun sendRegistrationMessage(to: String, subject: String, text: String) {
        try {
            val message = SimpleMailMessage()
            message.setTo(to)
            message.from = sender
            message.subject = subject
            message.text = text

            emailSender!!.send(message)
        } catch (exception: MailException) {
            exception.printStackTrace()
        }

    }

    override fun useTemplate(to: String,
                                                subject: String,
                                                template: String,
                                                params:MutableMap<String, Any>) {
        val message = emailSender!!.createMimeMessage()
        val helper = MimeMessageHelper(message, true, "utf-8")
        var context: Context = Context()
        context.setVariables(params)
        val html: String = templateEngine.process(template, context)

        helper.setTo(to)
        helper.setFrom(sender)
        helper.setText(html, true)
        helper.setSubject(subject)

        emailSender!!.send(message)
    }


    override fun sendHtmlMessage(to: String, subject: String, htmlMsg: String) {
        try {
            val message = emailSender!!.createMimeMessage()
            message.setContent(htmlMsg, "text/html")

            val helper = MimeMessageHelper(message, false, "utf-8")

            helper.setTo(to)
            helper.setFrom(sender)
            helper.setSubject(subject)

            emailSender!!.send(message)
        } catch (exception: MailException) {
            exception.printStackTrace()
        }
    }

    override fun sendRegistrationConfirmationEmail(user: User) {
        val token = UUID.randomUUID().toString()
        userDetailsService.createVerificationTokenForUser(token, user)
        val link = "$hostUrl/?token=$token&confirmRegistration=true"
        val msg = "<p>Click on link for registration confirmation:</p><p><a href=\"$link\">$link</a></p>"
        user.email?.let{sendHtmlMessage(user.email!!, "FiveStar: Registration Confirmation", msg)}
    }
}