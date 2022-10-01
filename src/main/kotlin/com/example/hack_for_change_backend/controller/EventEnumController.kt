package com.example.hack_for_change_backend.controller

import com.example.hack_for_change_backend.service.EventEnumService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin(origins = ["http://localhost:3000"])
@RequestMapping("/eventEnums")
class EventEnumController(private val eventEnumService: EventEnumService) {

    @GetMapping("/getAll")
    fun getAll() = ResponseEntity.ok().body(eventEnumService.getAllEventEnums())
}