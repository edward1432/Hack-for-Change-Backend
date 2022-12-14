package com.example.hack_for_change_backend.UserTest

import com.example.hack_for_change_backend.model.*
import com.example.hack_for_change_backend.model.enums.EventStatus
import com.example.hack_for_change_backend.repository.EventRepo
import com.example.hack_for_change_backend.service.EventService
import com.ninjasquad.springmockk.MockkBean
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.web.util.NestedServletException
import com.example.hack_for_change_backend.model.enums.EventType
import com.example.hack_for_change_backend.model.enums.PollStatus
import com.example.hack_for_change_backend.model.enums.UserRoles
import com.example.hack_for_change_backend.model.voting.Poll
import com.example.hack_for_change_backend.repository.OrganisationRepo
import com.example.hack_for_change_backend.repository.UserRepo

import org.junit.jupiter.api.Nested
import java.util.*

@AutoConfigureMockMvc // auto-magically configures and enables an instance of MockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// Why configure Mockito manually when a JUnit 5 test extension already exists for that very purpose?
@ExtendWith(SpringExtension::class, MockitoExtension::class)
class UserControllerTest {


    @Autowired
    private lateinit var mockMvc: MockMvc

    val votes1: MutableMap<EventType, Int> = mutableMapOf()

    val poll1 = mutableListOf<Poll>()

    val date1 = Date(2018, 12, 12)
    val date2 = Date(2020, 12 ,12 )

    val enjoyerMutableList = mutableListOf<User>()

    val eventMutableList = mutableListOf<Event>()

    val venueMutableList = mutableListOf<Venue>()

    val events = listOf<Event>()
    val enum = EventType.valueOf("CINEMA")

    val organisation1: Organisation = Organisation(1, "name", "Lewis", "email", "phoneNo", enjoyerMutableList, eventMutableList)
    val user1 = UserRoles.USER
    val enjoyer1: User = User("Alex", "Beeswax","email", "password", user1)

    val enjoyers: List<User> = listOf()


    val eventing: Event = Event(1, "location", "Alex", date1, date2, "description",  organisation1, poll1, PollStatus.OPEN, venueMutableList, enum, votes1, EventStatus.PROPOSED)

    val venue: Venue = Venue(1, events, "name", "location", enum)

    @MockkBean
    lateinit var repository: UserRepo

    @Test
    fun findUser() {
        mockMvc.get("/enjoyers/findAll")
            .andExpect {
                status { isOk() }
            }
        verify(repository, times(1)).findAll()
    }


    @Test
    fun bad_post_request_User(){
        val enjoyer: User = User("asd123", "Beeswax","email", "password", UserRoles.USER)
        assertThrows<NestedServletException> {
            mockMvc.post("/api/v1/registration/"){
                contentType = MediaType.APPLICATION_JSON
                content = jacksonObjectMapper().writeValueAsString(enjoyer)
                accept = MediaType.APPLICATION_JSON
            }
        }
        verify(repository, times(1)).save(enjoyer)
    }

    @Test
    fun good_post_request_User(){
        val enjoyer = enjoyer1
        mockMvc.post("/api/v1/registration"){
            contentType = MediaType.APPLICATION_JSON
            content = jacksonObjectMapper().writeValueAsString(enjoyer)
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            content{
                contentType(MediaType.APPLICATION_JSON)
            }
            content{
                json("""{"firstName":Alex,"lastName":"Beeswax", "email":"email", "password":"password", "role":"$user1"}""")
            }
        }
        verify(repository, times(1)).save(enjoyer)
    }



}