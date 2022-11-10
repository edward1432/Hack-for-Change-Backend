package com.example.hack_for_change_backend

import com.example.hack_for_change_backend.model.Organisation
import com.example.hack_for_change_backend.model.Post
import com.example.hack_for_change_backend.model.User
import com.example.hack_for_change_backend.model.enums.UserRoles
import com.example.hack_for_change_backend.service.EventService
import com.example.hack_for_change_backend.service.PostService
import com.example.hack_for_change_backend.service.UserService
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class HackForChangeBackendApplication {

    @Bean
    fun init(userService: UserService, eventService: EventService, postService: PostService) = CommandLineRunner {
        val user1 = User("Lewis", "Broadhurst", "lbroadhurst@gmail.com", "password", UserRoles.USER)
        val user2 = User("Edward", "Todd", "etodd@gmail.com", "password", UserRoles.USER)
        val user3 = User("Alex", "Leaver-Hernandez", "aleaverhernandez@gmail.com", "password", UserRoles.USER)
        val user4 = User("Jack", "Gilmore", "jgilmore@gmail.com", "password", UserRoles.USER)

//        val orgUsers = MutableList(user1, user2, user3, user4)

        userService.signUpUser(user1)
        userService.signUpUser(user2)
        userService.signUpUser(user3)
        userService.signUpUser(user4)


		eventService.addUserToEvent(1, 1)
		eventService.addUserToEvent(1, 2)
		eventService.addUserToEvent(1, 3)
		eventService.addUserToEvent(1, 4)

        postService.createPost(Post(1L, "1Who's going to the next quarterly?", 0, user1));
        postService.createPost(Post(2L, "2Who's going to the next quarterly?", 0, user2));
        postService.createPost(Post(3L, "3Who's going to the next quarterly?", 0, user3));
        postService.createPost(Post(4L, "4Who's going to the next quarterly?", 0, user4));
    }
}

fun main(args: Array<String>) {
    runApplication<HackForChangeBackendApplication>(*args)
}
//@Bean
//fun passwordEncoder(): PasswordEncoder {
//	return BCryptPasswordEncoder()
//}
