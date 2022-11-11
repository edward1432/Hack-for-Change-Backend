package com.example.hack_for_change_backend

import com.example.hack_for_change_backend.model.Post
import com.example.hack_for_change_backend.model.User
import com.example.hack_for_change_backend.model.enums.EventType
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

        val user5 = User("Margit", "Silversmidt", "msilversmidt0@123-reg.co.uk", "password", UserRoles.USER)
        val user6 = User("Constantine", "Critoph", "ccritoph1@devhub.com", "password", UserRoles.USER)
        val user7 = User("Wadsworth", "Christer", "wchrister2@myspace.com", "password", UserRoles.USER)
        val user8 = User("Becka", "Wesley", "bwesley3@webeden.co.uk", "password", UserRoles.USER)
        val user9 = User("Obed", "Grinston", "ogrinston4@google.pl", "password", UserRoles.USER)
        val user10 = User("Nathanael", "Ackenson", "nackenson5@ask.com", "password", UserRoles.USER)
        val user11 = User("Kaia", "Goodlud", "kgoodlud6@icio.us", "password", UserRoles.USER)
        val user12 = User("Haywood", "Parzizek", "hparzizek7@bing.com", "password", UserRoles.USER)
        val user13 = User("Ignace", "Stailey", "istailey8@pagesperso-orange.fr", "password", UserRoles.USER)

        userService.signUpUser(user1)
        userService.signUpUser(user2)
        userService.signUpUser(user3)
        userService.signUpUser(user4)
        userService.signUpUser(user5)
        userService.signUpUser(user6)
        userService.signUpUser(user7)
        userService.signUpUser(user8)
        userService.signUpUser(user9)
        userService.signUpUser(user10)
        userService.signUpUser(user11)
        userService.signUpUser(user12)
        userService.signUpUser(user13)


		eventService.addUserToEvent(1, 1)
		eventService.addUserToEvent(1, 2)
		eventService.addUserToEvent(1, 3)
		eventService.addUserToEvent(1, 4)
		eventService.addUserToEvent(1, 5)
		eventService.addUserToEvent(1, 6)
		eventService.addUserToEvent(1, 7)
		eventService.addUserToEvent(1, 8)
		eventService.addUserToEvent(1, 9)
		eventService.addUserToEvent(1, 10)
		eventService.addUserToEvent(1, 11)
		eventService.addUserToEvent(1, 12)
		eventService.addUserToEvent(1, 13)

        postService.createPost(Post(1L, "1Who's going to the next quarterly?", 0, user1), 1)
        postService.createPost(Post(2L, "2Who's going to the next quarterly?", 0, user2), 2)
        postService.createPost(Post(3L, "3Who's going to the next quarterly?", 0, user3), 3)
        postService.createPost(Post(4L, "4Who's going to the next quarterly?", 0, user4), 4)

        eventService.addVotes(1, 1, listOf(EventType.DINNER, EventType.CINEMA, EventType.BOWLING))
        eventService.addVotes(1, 2, listOf(EventType.CINEMA, EventType.DINNER, EventType.BOWLING))
        eventService.addVotes(1, 3, listOf(EventType.DINNER, EventType.CINEMA, EventType.BOWLING))
        eventService.addVotes(1, 4, listOf(EventType.BOWLING, EventType.DRINKS, EventType.CINEMA))
        eventService.addVotes(1, 5, listOf(EventType.DINNER))
    }
}

fun main(args: Array<String>) {
    runApplication<HackForChangeBackendApplication>(*args)
}
//@Bean
//fun passwordEncoder(): PasswordEncoder {
//	return BCryptPasswordEncoder()
//}
