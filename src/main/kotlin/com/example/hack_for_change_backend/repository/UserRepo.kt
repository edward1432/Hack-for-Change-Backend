package com.example.hack_for_change_backend.repository

import com.example.hack_for_change_backend.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface UserRepo : JpaRepository<User, Long> {
    companion object
    fun findByEmail(email: String?): User?
    @Query(value = "SELECT * FROM enjoyers WHERE organisation LIKE %:organisation%", nativeQuery = true)
    fun findByOrganisation(@Param("organisation") organisation: String): List<User>

    @Query(value = "SELECT * FROM enjoyers WHERE lower(first_name) LIKE lower(?1)", nativeQuery = true)
    fun findByFirstName(@Param("first_name") first_name: String): List<User>

    @Query(value = "SELECT * FROM enjoyers WHERE lower(last_name) LIKE lower(?1)", nativeQuery = true)
    fun findByLastName(@Param("last_name") last_name: String): List<User>

    @Modifying
    @Query(value = "UPDATE enjoyers SET event_id = ?1 WHERE id = ?2", nativeQuery = true)
    fun addUserToEvent(user_id: Long, event_id: Long)

    @Modifying
    @Query(value = "UPDATE enjoyers SET organisation_id = ?1 WHERE id = ?2", nativeQuery = true)
    fun addUserToOrganisation(user_id: Long, organisation_id: Long)

    @Modifying
    @Query(value = "UPDATE enjoyers SET venue_id = ?1 WHERE id = ?2", nativeQuery = true)
    fun addUserToVenue(user_id: Long, venue_id: Long)





}