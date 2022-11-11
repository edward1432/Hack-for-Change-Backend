package com.example.hack_for_change_backend.model

import com.example.hack_for_change_backend.model.enums.EventStatus
import java.util.*

data class EventDetailsModel(
    val location: String?,
    val name: String?,
    val startDateTime: Date?,
    val endDateTime: Date?,
    val status: EventStatus?
)
