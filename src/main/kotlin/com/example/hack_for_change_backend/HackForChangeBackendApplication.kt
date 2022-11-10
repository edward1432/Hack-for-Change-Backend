package com.example.hack_for_change_backend

import com.example.hack_for_change_backend.model.Organisation
import com.example.hack_for_change_backend.model.User
import com.example.hack_for_change_backend.model.enums.UserRoles
import com.example.hack_for_change_backend.service.EventService
import com.example.hack_for_change_backend.service.OrganisationService
import com.example.hack_for_change_backend.service.UserService
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.password.PasswordEncoder
import java.util.UUID

@SpringBootApplication
class HackForChangeBackendApplication {

    @Bean
    fun init(userService: UserService, eventService: EventService, organisationService: OrganisationService) = CommandLineRunner {
        userService.signUpUser(User("Lewis", "Broadhurst", "lbroadhurst@gmail.com", "password", UserRoles.USER))
        userService.signUpUser(User("Edward", "Todd", "etodd@gmail.com", "password", UserRoles.USER))
        userService.signUpUser(User("Alex", "Leaver-Hernandez", "aleaverhernandez@gmail.com", "password", UserRoles.USER))
        userService.signUpUser(User("Jack", "Gilmore", "jgilmore@gmail.com", "password", UserRoles.USER))

		eventService.addUserToEvent(1, 1)
		eventService.addUserToEvent(1, 2)
		eventService.addUserToEvent(1, 3)
		eventService.addUserToEvent(1, 4)

//        organisationService.addNewOrganisation(Organisation(1, "aaa", "aaa", "233271", mutableListOf(), mutableListOf()))
    }
}

fun main(args: Array<String>) {
    runApplication<HackForChangeBackendApplication>(*args)
}
//@Bean
//fun passwordEncoder(): PasswordEncoder {
//	return BCryptPasswordEncoder()
//}
