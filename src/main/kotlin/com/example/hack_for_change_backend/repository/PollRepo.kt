package com.example.hack_for_change_backend.repository

import com.example.hack_for_change_backend.model.voting.Poll
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface PollRepo : JpaRepository<Poll, Long> {
    companion object

    fun findByEventUniqueIdIs(eventId: Long): List<Poll>

    @Query(value = "select * from polls where event_unique_id = ?1", nativeQuery = true)
    fun findByEventId(eventId: Long): List<Poll>

    @Query(value = "select * from polls where event_unique_id = ?1 and user_unique_id = ?2", nativeQuery = true)
    fun findByEventIdAndUserId(eventId: Long, userId: Long): List<Poll>
}