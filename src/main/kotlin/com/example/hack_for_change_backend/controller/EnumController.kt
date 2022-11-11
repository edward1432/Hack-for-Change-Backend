package com.example.hack_for_change_backend.controller

import com.example.hack_for_change_backend.service.EnumService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin(origins = ["http://localhost:3000"])
@RequestMapping("/enums")
class EnumController(private val enumService: EnumService) {
    @GetMapping("/getAllEventTypes")
    fun getAllTypes() = ResponseEntity.ok().body(enumService.getAllEventEnums())

    @GetMapping("/getAllEventStatusOptions")
    fun getAllStatusOptions() = ResponseEntity.ok().body(enumService.getAllEventStatusEnums())

    @GetMapping("/getAllPollStatusOptions")
    fun getAllPollStatusOptions() = ResponseEntity.ok().body(enumService.getAllPollStatusEnums())

    @GetMapping("/getAllRSVPOptions")
    fun getAllRSVPOptions() = ResponseEntity.ok().body(enumService.getAllRSVPOptions())
}