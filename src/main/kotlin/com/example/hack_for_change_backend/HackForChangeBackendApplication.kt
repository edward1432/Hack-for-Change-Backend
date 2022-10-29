package com.example.hack_for_change_backend

import com.example.hack_for_change_backend.model.enums.EventType
import com.example.hack_for_change_backend.repository.EventRepo
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@SpringBootApplication
class HackForChangeBackendApplication

fun main(args: Array<String>) {
	runApplication<HackForChangeBackendApplication>(*args)

}

//@Bean
//fun passwordEncoder(): PasswordEncoder {
//	return BCryptPasswordEncoder()
//}
