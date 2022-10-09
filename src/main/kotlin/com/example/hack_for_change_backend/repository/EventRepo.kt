package com.example.hack_for_change_backend.repository

import com.example.hack_for_change_backend.model.Event
import com.example.hack_for_change_backend.model.enums.EventType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface EventRepo : JpaRepository<Event, Long> {

    fun findByEventTypeIs(eventType: EventType): List<Event>

}