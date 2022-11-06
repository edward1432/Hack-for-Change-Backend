package com.example.hack_for_change_backend.controller

import com.example.hack_for_change_backend.model.Event
import com.example.hack_for_change_backend.model.enums.EventType
import com.example.hack_for_change_backend.service.EventService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.lang.IllegalArgumentException

@RestController
@CrossOrigin(origins = ["http://localhost:3000"])
@RequestMapping("/events")
class EventController(private val eventService: EventService) {

    @GetMapping("/findAllEvents")
    fun getAllEvents(): ResponseEntity<List<Event>> = ResponseEntity.ok(eventService.findAll())

    @GetMapping("/findEventById/{id}")
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

    @PutMapping("/addEmployee/{event_id}/{employee_id}")
    fun addEmployeeToEvent(@PathVariable("event_id") eventId: Long, @PathVariable("employee_id") employeeId: Long): ResponseEntity<HttpStatus> {
        return try {
            eventService.addEmployeeToEvent(eventId, employeeId)
            ResponseEntity.ok().body(HttpStatus.OK)
        } catch (e: NoSuchElementException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message)
        }
    }
}