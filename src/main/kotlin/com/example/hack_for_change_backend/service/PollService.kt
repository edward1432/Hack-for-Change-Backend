package com.example.hack_for_change_backend.service

import com.example.hack_for_change_backend.model.Event
import com.example.hack_for_change_backend.model.User
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

    fun addNewPoll(event: Event, user: User): Poll {
        if (pollRepo.findByEventIdAndUserId(event.uniqueId, user.uniqueId).isNotEmpty()) {
            throw Exception("Poll with event ID ${event.uniqueId} and user ID ${user.uniqueId} already present")
        }
        return pollRepo.save(Poll(0, user, event, mutableListOf(), null))
    }
}