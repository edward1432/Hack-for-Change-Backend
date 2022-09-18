package com.example.hack_for_change_backend.repository

import com.example.hack_for_change_backend.model.Venue
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface VenueRepo : JpaRepository<Venue, Long> {
    companion object
}