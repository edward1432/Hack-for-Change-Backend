package com.example.hack_for_change_backend.service

import com.example.hack_for_change_backend.model.Venue
import com.example.hack_for_change_backend.repository.VenueRepo
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.lang.IllegalArgumentException

@Service
class VenueService(val venueRepo: VenueRepo) {
    fun findVenueById(id: Long): Venue{
        return venueRepo.findById(id).orElseThrow {
            NoSuchElementException("[VENUE] $id NOT FOUND")
        }
    }

    fun findAll(): List<Venue>{
        return venueRepo.findAll()
    }

    fun checkVenueExists(venue: Venue): Boolean = findAll().contains(venue)

    fun createVenue(venue: Venue): ResponseEntity<Venue> {
        if (!checkVenueExists(venue)) {
            venueRepo.save(venue)
            return ResponseEntity.ok(venue)
        } else {
            throw IllegalArgumentException("[VENUE] $venue ALREADY EXISTS")
        }
    }


    fun updateVenue(venueId: Long, venueDetails: Venue): ResponseEntity<Venue> {
        try {
            val venue = findVenueById(venueId)
            venue.name = venueDetails.name
            venue.location = venueDetails.location
//            venue.type = venueDetails.type
            return ResponseEntity.ok(venue)
        } catch (e: NoSuchElementException) {
            throw NoSuchElementException(e.message)
        }
    }

    fun deleteVenue(venueId: Long): ResponseEntity<HttpStatus> {
        try {
            venueRepo.delete(findVenueById(venueId))
            return ResponseEntity.ok(HttpStatus.OK)
        } catch (e: NoSuchElementException) {
            throw NoSuchElementException(e.message)
        }
    }
}
