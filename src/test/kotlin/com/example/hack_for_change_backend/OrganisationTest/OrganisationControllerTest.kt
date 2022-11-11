package com.example.hack_for_change_backend.OrganisationTest

import com.example.hack_for_change_backend.model.User
import com.ninjasquad.springmockk.MockkBean
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest

import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.web.util.NestedServletException
import com.example.hack_for_change_backend.model.Event
import com.example.hack_for_change_backend.model.Organisation
import com.example.hack_for_change_backend.model.Venue
import com.example.hack_for_change_backend.model.enums.EventStatus
import com.example.hack_for_change_backend.model.enums.EventType
import com.example.hack_for_change_backend.model.enums.PollStatus
import com.example.hack_for_change_backend.model.enums.UserRoles
import com.example.hack_for_change_backend.model.voting.Poll
import com.example.hack_for_change_backend.repository.OrganisationRepo
import java.util.*


@AutoConfigureMockMvc // auto-magically configures and enables an instance of MockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// Why configure Mockito manually when a JUnit 5 test extension already exists for that very purpose?
@ExtendWith(SpringExtension::class, MockitoExtension::class)
class OrganisationControllerTest {


    @Autowired
    private lateinit var mockMvc: MockMvc

    val votes1: MutableMap<EventType, Int> = TODO()

    val poll1 = mutableListOf<Poll>()

    val date1: Date

    val date2: Date

    val enjoyerMutableList = mutableListOf<User>()

    val eventMutableList = mutableListOf<Event>()

    val venueMutableList = mutableListOf<Venue>()

    val events = listOf<Event>()
    val enum = EventType.valueOf("cinema")

    val organisation1: Organisation = Organisation(1, "name", "Lewis", "email", "phoneNo", enjoyerMutableList, eventMutableList)

    val enjoyer: User = User("Alex", "Beeswax","email", "password", UserRoles.USER)

    val enjoyers: List<User> = listOf()


    val eventing: Event = Event(1, "location", "Alex", date1, date2, "description",  organisation1, poll1, PollStatus.OPEN, venueMutableList, enum, votes1, EventStatus.PROPOSED)

    val venue: Venue = Venue(1, events, "name", "location", enum)



    @MockkBean
    lateinit var repository: OrganisationRepo

    @Test
    fun findEvent() {
        mockMvc.get("/organisations?id=1")
            .andExpect {
                status { isOk() }
            }
        verify(repository, times(1)).findAll()
    }


    @Test
    fun bad_post_request_event(){
        val organisation = Organisation(1, "23", "Lewis", "email", "phoneNo", enjoyerMutableList, eventMutableList)
        assertThrows<NestedServletException> {
            mockMvc.post("/organisations/"){
                contentType = MediaType.APPLICATION_JSON
                content = jacksonObjectMapper().writeValueAsString(organisation)
                accept = MediaType.APPLICATION_JSON
            }
        }
        verify(repository, times(1)).save(organisation)
    }


    @Test
    fun good_post_request_event(){
        val organisation = organisation1
        mockMvc.post("/organisations/"){
            contentType = MediaType.APPLICATION_JSON
            content = jacksonObjectMapper().writeValueAsString(organisation)
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            content{
                contentType(MediaType.APPLICATION_JSON)
            }
            content{
                json("""{"uniqueId":1,"joinCode":"name", "name":"Lewis","email":"email", "phoneNo":"phoneNo,"enjoyers":"$enjoyerMutableList","events":"$eventMutableList"}""")
            }
        }
        verify(repository, times(1)).save(organisation)
    }



}