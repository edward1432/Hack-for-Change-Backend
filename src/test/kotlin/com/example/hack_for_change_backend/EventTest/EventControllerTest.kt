package com.example.hack_for_change_backend.EventTest

import com.example.hack_for_change_backend.model.Enjoyer
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
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
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
import com.example.hack_for_change_backend.model.Event
import com.example.hack_for_change_backend.model.Organisation
import com.example.hack_for_change_backend.model.Venue
import com.example.hack_for_change_backend.model.enums.EventType

import org.junit.jupiter.api.Nested

@AutoConfigureMockMvc // auto-magically configures and enables an instance of MockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// Why configure Mockito manually when a JUnit 5 test extension already exists for that very purpose?
@ExtendWith(SpringExtension::class, MockitoExtension::class)
class EventControllerTest() {

    @Autowired
    private lateinit var mockMvc: MockMvc

    val enjoyerMutableList = mutableListOf<Enjoyer>()

    val eventMutableList = mutableListOf<Event>()

    val venueMutableList = mutableListOf<Venue>()

    val events = listOf<Event>()
    val enum = EventType.valueOf("cinema")

    val organisation: Organisation = Organisation(1, "name", "email", "phoneNo",enjoyerMutableList, eventMutableList)

    val enjoyer: Enjoyer = Enjoyer(1, "Alex", organisation, events)

    val enjoyers: List<Enjoyer> = listOf()


    val eventing: Event = Event(1, "location", "date", organisation, venueMutableList, enum, enjoyers)

    val venue: Venue = Venue(1, eventing, "name", "location", enum)



    @MockkBean
    lateinit var repository: EventRepo

    @Test
    fun findEvent() {
        mockMvc.get("/events?id=1")
            .andExpect {
                status { isOk() }
            }
        verify(repository, times(1)).findAll()
    }


    @Test
    fun bad_post_request_event(){
        val event = Event(3, "Home", "Wed Mar 27 08:22:02 IST 2015", organisation, venueMutableList, enum, enjoyers )
        assertThrows<NestedServletException> {
            mockMvc.post("/events/"){
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
        mockMvc.post("/events/"){
            contentType = MediaType.APPLICATION_JSON
            content = jacksonObjectMapper().writeValueAsString(event)
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            content{
                contentType(MediaType.APPLICATION_JSON)
            }
            content{
                 json("""{"id":1,"name":"Test"}""")
            }
        }
        verify(repository, times(1)).save(event)
    }

}
