package com.example.hack_for_change_backend.EventTest

import com.example.hack_for_change_backend.model.User
import com.example.hack_for_change_backend.repository.EventRepo

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
import io.mockk.Ordering
import java.time.LocalDate

import java.util.Date

@AutoConfigureMockMvc // auto-magically configures and enables an instance of MockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// Why configure Mockito manually when a JUnit 5 test extension already exists for that very purpose?
@ExtendWith(SpringExtension::class, MockitoExtension::class)
class EventControllerTest() {

    @Autowired
    private lateinit var mockMvc: MockMvc

    val votes1: MutableMap<EventType, Int> = mutableMapOf()

    val poll1 = mutableListOf<Poll>()

    val date1 = Date(2018, 12, 12)
    val date2 = Date(2020, 12 ,12 )

    val enjoyerMutableList = mutableListOf<User>()

    val eventMutableList = mutableListOf<Event>()

    val venueMutableList = mutableListOf<Venue>()
    val pollstatus1 = PollStatus.OPEN
    val events = listOf<Event>()
    val enum = EventType.valueOf("CINEMA")
    val eventStatus1 = EventStatus.PROPOSED
    val organisation: Organisation = Organisation(1, "name", "Lewis", "email", "phoneNo", enjoyerMutableList, eventMutableList)

    val enjoyer: User = User("Alex", "Beeswax","email", "password", UserRoles.USER)

    val enjoyers: List<User> = listOf()


    val eventing: Event = Event(1, "location", "Alex", date1, date2, "description",  organisation, poll1, pollstatus1, venueMutableList, enum, votes1, eventStatus1)

    val venue: Venue = Venue(1, events, "name", "location", enum)



    @MockkBean
    lateinit var repository: EventRepo

    @Test
    fun findEvent() {
        mockMvc.get("/events/findAll")
            .andExpect {
                status { isOk() }
            }
        verify(repository, times(1)).findAll()
    }


    @Test
    fun bad_post_request_event(){
        val event = Event(3, "Home", "NoWhere", date1, date2, "wrong", organisation, poll1, PollStatus.CLOSED, venueMutableList, enum, votes1, EventStatus.CANCELLED)
        assertThrows<NestedServletException> {
            mockMvc.post("/events/addEvent"){
                contentType = MediaType.APPLICATION_JSON
                content = jacksonObjectMapper().writeValueAsString(event)
                accept = MediaType.APPLICATION_JSON
            }
        }
        verify(repository, times(1)).save(event)
    }

    @Test
    fun good_post_request_event(){
        val event = eventing
        mockMvc.post("/events/addEvent"){
            contentType = MediaType.APPLICATION_JSON
            content = jacksonObjectMapper().writeValueAsString(event)
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            content{
                contentType(MediaType.APPLICATION_JSON)
            }
            content{
                 json("""{"uniqueId":1,"location":"location","name":"Alex","startDateTime":"$date1", "endDateTime": "$date2", "description":"description","organisation":"$organisation","userPolls":"$poll1",pollStatus":"$pollstatus1", "venues":"$venueMutableList", "eventType":"$enum", "votes":"$votes1", "status":"$eventStatus1""}""")
            }
        }
        verify(repository, times(1)).save(event)
    }

}
