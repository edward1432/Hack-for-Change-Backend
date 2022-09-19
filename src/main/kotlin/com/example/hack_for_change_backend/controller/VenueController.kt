package com.example.hack_for_change_backend.controller

import com.example.hack_for_change_backend.model.User
import com.example.hack_for_change_backend.model.Venue
import com.example.hack_for_change_backend.service.UserService
import com.example.hack_for_change_backend.service.VenueService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/venues")
class VenueController(private val venueService: VenueService) {
    @GetMapping("/findAll")
    fun getAllVenues(): ResponseEntity<List<Venue>> = ResponseEntity.ok(venueService.findAll())

    @GetMapping("/findById/{id}")
    fun findVenueById(@PathVariable("id") venueId: Long): ResponseEntity<Venue> {
        return try {
            ResponseEntity.ok(venueService.findVenueById(venueId))
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message)
        }
    }

    @PostMapping("/addVenue")
    fun addVenue(@RequestBody venue: Venue): ResponseEntity<Venue> {
        return try {
            venueService.createVenue(venue)
        }catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message)
        }
    }

    @PutMapping("/updateVenue/{id}")
    fun updateVenue(@PathVariable("id") venueId: Long, @RequestBody venue: Venue): ResponseEntity<Venue> {
        return try {
            venueService.updateVenue(venueId,venue)
        }catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message)
        }
    }

}