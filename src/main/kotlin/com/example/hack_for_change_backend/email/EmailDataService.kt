package com.example.hack_for_change_backend.email

import com.example.hack_for_change_backend.model.User


interface EmailService {
    fun sendRegistrationMessage(to: String,
                          subject: String,
                          text: String)

    fun useTemplate(to: String,
                                       subject: String,
                                       template: String,
                                       params:MutableMap<String, Any>)


    fun sendHtmlMessage(to: String,
                        subject: String,
                        htmlMsg: String)

    fun sendRegistrationConfirmationEmail(user: User)
}