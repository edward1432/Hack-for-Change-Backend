package com.example.hack_for_change_backend.model.enums

import com.fasterxml.jackson.annotation.JsonFormat

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
enum class EventType(val niceName: String) {
    BOWLING("Bowling"),
    CINEMA("Cinema"),
    DINNER("Dinner"),
    LUNCH("Lunch"),
    DRINKS("Drinks"),
    GOLF("Golf"),
    GOKARTING("Go-Karting")
}