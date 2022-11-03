package com.example.hack_for_change_backend.service

import com.example.hack_for_change_backend.model.enums.EventStatus
import com.example.hack_for_change_backend.model.enums.EventType
import org.springframework.stereotype.Service

@Service
class EventEnumService {

    fun getAllEventEnums(): List<EventType> = EventType.values().toList()

    fun getAllEventStatusEnums(): List<EventStatus> = EventStatus.values().toList()
}