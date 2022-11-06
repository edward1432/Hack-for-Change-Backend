package com.example.hack_for_change_backend.controller

import com.example.hack_for_change_backend.model.voting.Poll
import com.example.hack_for_change_backend.service.PollService
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
@CrossOrigin(origins = ["http://localhost:3000"])
@RequestMapping("/polls")
class PollController (private val pollService: PollService) {

    @GetMapping("/findByEventId/{id}")
    fun findByEventId(@PathVariable("id") eventId: Long): ResponseEntity<List<Poll>> {
        return try {
            ResponseEntity.ok(pollService.findPollByEventId(eventId))
        } catch (e: NotFoundException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message)
        }
    }
}