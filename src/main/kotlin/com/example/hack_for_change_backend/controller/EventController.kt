package com.example.hack_for_change_backend.controller

import com.example.hack_for_change_backend.model.Event
import com.example.hack_for_change_backend.model.enums.EventStatus
import com.example.hack_for_change_backend.model.enums.EventType
import com.example.hack_for_change_backend.model.enums.RSVP
import com.example.hack_for_change_backend.model.voting.Poll
import com.example.hack_for_change_backend.service.EventService
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.data.repository.query.Param
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.lang.IllegalArgumentException
import java.util.*
import kotlin.NoSuchElementException

@RestController
@CrossOrigin(origins = ["http://localhost:3000"])
@RequestMapping("/events")
class EventController(private val eventService: EventService) {

    @GetMapping("/findAll")
    fun getAllEvents(): ResponseEntity<List<Event>> = ResponseEntity.ok(eventService.findAll())

    @GetMapping("/findById/{id}")
    fun findEventById(@PathVariable("id") eventId: Long): ResponseEntity<Event> {
        return try {
            ResponseEntity.ok(eventService.findEventById(eventId))
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message)
        }
    }

    @GetMapping("/findByEventTypeIs")
    fun findEventByType(@RequestParam eventType: EventType): ResponseEntity<List<Event>> {
        return ResponseEntity.ok(eventService.findEventByType(eventType))
    }

    @PostMapping("/addEvent")
    fun addEvent(@RequestBody event: Event, @RequestParam organisationId: Long): ResponseEntity<Event> {
        return try {
            ResponseEntity.ok().body(eventService.addNewEvent(event, organisationId))
        } catch (e: NoSuchElementException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message)
        } catch (e: IllegalArgumentException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message)
        }
    }

    @PutMapping("/updateEvent/{id}")
    fun updateEvent(@PathVariable("id") eventId: Long, @RequestBody updatedEvent: Event): ResponseEntity<Event> {
        return try {
            ResponseEntity.ok().body(eventService.updateEvent(eventId, updatedEvent))
        } catch (e: NoSuchElementException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message)
        }
    }

    @DeleteMapping("/deleteEvent/{id}")
    fun deleteEvent(@PathVariable("id") eventId: Long): ResponseEntity<HttpStatus> {
        return try {
            eventService.deleteEvent(eventId)
            ResponseEntity.ok().body(HttpStatus.OK)
        } catch (e: NoSuchElementException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message)
        }
    }

    @PutMapping("/addUser/{event_id}/{user_id}")
    fun addUserToEvent(
        @PathVariable("event_id") eventId: Long,
        @PathVariable("user_id") userId: Long):
            ResponseEntity<HttpStatus> {
        return try {
            eventService.addUserToEvent(eventId, userId)
            ResponseEntity.ok().body(HttpStatus.OK)
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message)
        }
    }

    @PatchMapping("/addVotes/{event_id}/{user_id}")
    fun addVotes(@PathVariable("event_id") eventId: Long,
                 @PathVariable("user_id") userId: Long,
                 @RequestParam("ballot", required = true) ballot: List<EventType>): ResponseEntity<Poll> {
        return try {
            ResponseEntity.ok(eventService.addVotes(eventId, userId, ballot))
        } catch (e: NotFoundException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message)
        }
    }

    @PatchMapping("/closePoll/{event_id}")
    fun closePoll(@PathVariable("event_id") eventId: Long): ResponseEntity<Pair<Event, String>> {
        return try {
            ResponseEntity.ok(eventService.closePoll(eventId))
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, e.message)
        }
    }

//    @PatchMapping("/editDetails/{id}")
//    fun editEvent(@PathVariable("event_id") eventId: Long,
//                  @RequestParam("location", required = false) location: String,
//                  @RequestParam("name", required = false) name: String,
//                  @RequestParam("start_date_time", required = false) startDateTime: Date,
//                  @RequestParam("end_date_time", required = false) endDateTime: Date,
//                  @RequestParam("status", required = false) eventStatus: EventStatus): ResponseEntity<Event> {
//
//    }

}