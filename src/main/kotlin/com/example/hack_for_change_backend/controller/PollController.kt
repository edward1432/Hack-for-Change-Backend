package com.example.hack_for_change_backend.controller

import com.example.hack_for_change_backend.model.enums.EventType
import com.example.hack_for_change_backend.model.enums.RSVP
import com.example.hack_for_change_backend.model.voting.Poll
import com.example.hack_for_change_backend.service.PollService
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import java.lang.AssertionError

@RestController
@CrossOrigin(origins = ["http://localhost:3000"])
@RequestMapping("/polls")
class PollController (private val pollService: PollService) {

    @GetMapping("/findById/{id}")
    fun findById(@PathVariable("id") pollId: Long): ResponseEntity<Poll> {
        return try {
            ResponseEntity.ok(pollService.findPollById(pollId))
        } catch (e: NotFoundException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message)
        }
    }

    @GetMapping("/findByEventId/{id}")
    fun findByEventId(@PathVariable("id") eventId: Long): ResponseEntity<List<Poll>> {
        return try {
            ResponseEntity.ok(pollService.findPollByEventId(eventId))
        } catch (e: NotFoundException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message)
        }
    }

//    @PatchMapping("/rsvp/{id}")
//    fun rsvpToEvent(@PathVariable("id") pollId: Long,
//                    @RequestParam("rsvp", required = false) rsvp: RSVP?): ResponseEntity<Poll> {
//        return try {
//            ResponseEntity.ok(pollService.rsvp(pollId, rsvp))
//        } catch (e: NoSuchElementException) {
//            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message)
//        }
//    }

    @PatchMapping("/rsvp/{event_id}/{user_id}")
    fun rsvpToEvent(@PathVariable("event_id") eventId: Long,
                    @PathVariable("user_id") userId: Long,
                    @RequestParam("rsvp", required = false) rsvp: RSVP?): ResponseEntity<Poll> {
        return try {
            ResponseEntity.ok(pollService.rsvp(eventId, userId, rsvp))
        } catch (e: NoSuchElementException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message)
        }
    }
}