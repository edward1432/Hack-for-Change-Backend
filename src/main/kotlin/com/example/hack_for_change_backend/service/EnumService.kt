package com.example.hack_for_change_backend.service

import com.example.hack_for_change_backend.model.enums.EventStatus
import com.example.hack_for_change_backend.model.enums.EventType
import com.example.hack_for_change_backend.model.enums.PollStatus
import com.example.hack_for_change_backend.model.enums.RSVP
import org.springframework.stereotype.Service

@Service
class EnumService {

    fun getAllEventEnums(): List<EventType> = EventType.values().toList()

    fun getAllEventStatusEnums(): List<EventStatus> = EventStatus.values().toList()

    fun getAllPollStatusEnums(): List<PollStatus> = PollStatus.values().toList()

    fun getAllRSVPOptions(): List<RSVP> = RSVP.values().toList()
}