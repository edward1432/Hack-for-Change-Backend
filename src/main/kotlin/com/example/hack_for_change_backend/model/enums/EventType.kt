package com.example.hack_for_change_backend.model.enums

import com.fasterxml.jackson.annotation.JsonFormat
import org.codehaus.jackson.annotate.JsonProperty

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
enum class EventType(val niceName: String) {
    BOWLING("Bowling"),
    CINEMA("Cinema"),
    DINNER("Dinner"),
    LUNCH("Lunch"),
    DRINKING("Drinking"),
    GOLF("Golf"),
    GOKARTING("Go-Karting")
}