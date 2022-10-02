package com.example.hack_for_change_backend.controller

import com.example.hack_for_change_backend.model.Event
import com.example.hack_for_change_backend.model.enums.EventType
import com.example.hack_for_change_backend.service.EventService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
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
    fun addEvent(@RequestBody event: Event): ResponseEntity<Event> {
        return try {
            ResponseEntity.ok().body(eventService.addNewEvent(event))
        } catch (e: NoSuchElementException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message)
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
}