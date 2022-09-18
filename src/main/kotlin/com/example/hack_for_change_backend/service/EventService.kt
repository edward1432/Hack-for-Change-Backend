package com.example.hack_for_change_backend.service

import com.example.hack_for_change_backend.repository.EventRepo
import org.springframework.stereotype.Service

@Service
class EventService {

    val eventRepo = EventRepo

    fun findById(id: Long) = eventRepo
}