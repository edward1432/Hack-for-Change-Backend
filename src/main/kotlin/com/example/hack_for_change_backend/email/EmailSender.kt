package com.example.hack_for_change_backend.email

interface EmailSender {
    fun send(to: String?, email: String?)
}