package com.example.hack_for_change_backend.service

import com.example.hack_for_change_backend.model.Poll
import com.example.hack_for_change_backend.repository.PollRepo
import com.sun.java.accessibility.util.EventID
import org.springframework.stereotype.Service

@Service
class PollService (val pollRepo: PollRepo, val eventService: EventService) {

//    fun createPoll(eventID: Long): Poll {
//        val poll = Poll
//        pollRepo.save()
//    }
}