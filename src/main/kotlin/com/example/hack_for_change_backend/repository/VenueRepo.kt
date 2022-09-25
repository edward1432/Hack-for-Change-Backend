package com.example.hack_for_change_backend.repository

import com.example.hack_for_change_backend.model.Organisation
import com.example.hack_for_change_backend.model.Venue
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface VenueRepo : JpaRepository<Venue, Long> {
    companion object

    @Query(value = "SELECT * FROM venues WHERE lower(venues) LIKE lower(?1)", nativeQuery = true)
    fun findVenueByName(@Param("venues") venues: String): List<Venue>

}


