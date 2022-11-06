package com.example.hack_for_change_backend.service

import com.example.hack_for_change_backend.model.voting.Poll
import com.example.hack_for_change_backend.repository.PollRepo
import org.springframework.stereotype.Service
import org.webjars.NotFoundException

@Service
class PollService (
    val pollRepo: PollRepo
        ) {

    fun findPollByEventId(eventId: Long): List<Poll> {
        return pollRepo.findByEventUniqueIdIs(eventId)
    }
}