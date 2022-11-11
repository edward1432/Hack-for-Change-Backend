package com.example.hack_for_change_backend.service

import com.example.hack_for_change_backend.model.Event
import com.example.hack_for_change_backend.model.User
import com.example.hack_for_change_backend.model.enums.EventType
import com.example.hack_for_change_backend.model.enums.PollStatus
import com.example.hack_for_change_backend.model.enums.RSVP
import com.example.hack_for_change_backend.model.voting.Poll
import com.example.hack_for_change_backend.repository.PollRepo
import org.springframework.stereotype.Service

@Service
class PollService (
    val pollRepo: PollRepo
        ) {

    fun findPollByEventId(eventId: Long): List<Poll> {
        return pollRepo.findByEventUniqueIdIs(eventId)
    }

    fun findPollById(id: Long): Poll = pollRepo.findById(id).orElseThrow { NoSuchElementException("Poll with ID: $id does not exist") }

    fun findPollByEventIdUserId(eventId: Long, userId: Long): Poll = pollRepo.findByEventIdAndUserId(eventId, userId) ?:
    throw NoSuchElementException("Poll with eventID: $eventId and userID: $userId not found")

    fun addNewPoll(event: Event, user: User): Poll {
        if (pollRepo.findByEventIdAndUserId(event.uniqueId, user.uniqueId) != null) {
            throw Exception("Poll with event ID ${event.uniqueId} and user ID ${user.uniqueId} already present")
        }
        return pollRepo.save(Poll(0, user, event, mutableListOf(), null))
    }

//    fun addVotes(pollId: Long, ballot: List<EventType>): Poll {
//
//        if (findPollById(pollId).event.pollStatus == PollStatus.CLOSED) throw Exception("Poll with ID: $pollId is closed")
//
//        return try {
//            val poll = findPollById(pollId)
//            poll.ballot = ballot.toMutableList()
//            pollRepo.save(poll)
//        } catch (e: NoSuchElementException) {
//            throw NoSuchElementException(e.message)
//        } catch (e: AssertionError) {
//            throw AssertionError(e.message)
//        }
//    }

    fun addVotesFromEvent(user: User, eventId: Long, ballot: List<EventType>): Poll {
        val poll = pollRepo.findByEventIdAndUserId(eventId, user.uniqueId) ?: throw NoSuchElementException("User with ID: ${user.uniqueId} not added to event ID: $eventId")
        poll.ballot = ballot.toMutableList()
        return pollRepo.save(poll)
    }

//    fun rsvp(pollId: Long, rsvp: RSVP?): Poll {
//        return try {
//            val poll = findPollById(pollId)
//            poll.rsvp = rsvp
//            pollRepo.save(poll)
//        } catch (e: NoSuchElementException) {
//            throw NoSuchElementException(e.message)
//        }
//    }

    fun rsvp(eventId: Long, userId: Long, rsvp: RSVP?): Poll {
        return try {
            val poll = findPollByEventIdUserId(eventId, userId)
            poll.rsvp = rsvp
            pollRepo.save(poll)
        } catch (e: NoSuchElementException) {
            throw NoSuchElementException(e.message)
        }
    }
}