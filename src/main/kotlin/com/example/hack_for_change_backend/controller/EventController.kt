package com.example.hack_for_change_backend.controller

import com.example.hack_for_change_backend.model.Event
import com.example.hack_for_change_backend.service.EventService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import java.lang.Exception

@RestController
@RequestMapping("/events")

class EventController(private val eventService: EventService) {

    @GetMapping("/findall")
    fun getAllEvents(): ResponseEntity<List<Event>> = ResponseEntity.ok(eventService.findAll())

    @GetMapping("/findbyid/{id}")
    fun findEventById(@PathVariable("id") eventId: Long): ResponseEntity<Event> {
        return try {
            ResponseEntity.ok(eventService.findEventById(eventId))
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message)
        }
    }

    @PostMapping("/addevent/{id}")
    fun addEvent(@PathVariable("id") organisationId: Long, @RequestBody event: Event) {
        return try {
            ResponseEntity.ok(eventService.)
        }
    }

    @PutMapping("/updateevent/{id}")
}