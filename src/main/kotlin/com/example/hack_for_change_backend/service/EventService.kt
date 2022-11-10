package com.example.hack_for_change_backend.service

import com.example.hack_for_change_backend.model.Event
import com.example.hack_for_change_backend.model.enums.EventStatus
import com.example.hack_for_change_backend.model.enums.EventType
import com.example.hack_for_change_backend.model.enums.PollStatus
import com.example.hack_for_change_backend.model.voting.Poll
import com.example.hack_for_change_backend.repository.EventRepo
import org.springframework.context.annotation.Bean
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.stereotype.Service
import kotlin.math.min

@Service
class EventService(
    val eventRepo: EventRepo,
    val organisationService: OrganisationService,
    val userService: UserService,
    val pollService: PollService
    ) {

    fun findEventById(id: Long): Event = eventRepo.findById(id).orElseThrow { NoSuchElementException("Event with ID: $id does not exist") }

    fun findEventByType(eventType: EventType): List<Event> = eventRepo.findByEventTypeIs(eventType)

    fun findAll(): List<Event> = eventRepo.findAll()

    fun checkEventExists(event: Event) = findAll().any {
        it.run {
            location == event.location
                    && eventType == event.eventType
//                    && venues == event.venues
//                    && startDateTime == event.startDateTime
//                    && endDateTime == event.endDateTime
        }
    }

    fun addNewEvent(event: Event, organisationId: Long): Event {
        if (checkEventExists(event)) throw IllegalArgumentException("Event $event already exists")
        return try {
            event.organisation = organisationService.findOrganisationById(organisationId)
//            event.status = EventStatus.PROPOSED
            eventRepo.save(event)
            event
        } catch (e: NoSuchElementException) {
            throw NoSuchElementException(e.message)
        }
    }

//    lateinit var eventServiceManager: List<Event>
//
//        fun createEvent(event: Event): ResponseEntity<Event> {
//        val eventManager = (eventServiceManager.find { eventList -> eventList == event })
//        if (eventManager != null) {
//            throw IllegalStateException("[EVENT] $event ALREADY EXISTS")
//        }
//        eventRepo.save(event)
//        return ResponseEntity.ok(event)
//    }

    // ===
    fun updateEvent(eventId: Long, eventDetails: Event): Event {
        try {
            val event = findEventById(eventId)
//            event.eventType = eventDetails.eventType
            event.organisation = eventDetails.organisation
            event.startDateTime = eventDetails.startDateTime
            event.venues = eventDetails.venues
            event.location = eventDetails.location
            event.status = eventDetails.status
            eventRepo.save(event)
            return event
        } catch (e: NoSuchElementException){
            throw NoSuchElementException(e.message)
        }
    }

    fun deleteEvent(eventId: Long) {
        try {
            eventRepo.delete(findEventById(eventId))
        } catch (e: NoSuchElementException) {
            throw NoSuchElementException(e.message)
        }
    }

//    fun addEmployeeToEvent(eventID: Long, employeeID: Long) {
//        try {
//            val event = findEventById(eventID)
//            event.users.add(userService.findUserById(employeeID))
//            eventRepo.save(event)
//        } catch (e: NoSuchElementException) {
//            throw NoSuchElementException(e.message)
//        }
//    }

    fun addUserToEvent(eventId: Long, userId: Long): Boolean {
        return try {
            val event = findEventById(eventId)
            val user = userService.findUserById(userId)
            pollService.addNewPoll(event, user)
            true
        } catch (e: Exception) {
            throw Exception(e.message)
        }
    }

    fun addVotes(eventId: Long, userId: Long, ballot: List<EventType>): Poll {
        return try {
            val event = findEventById(eventId)
            if (event.pollStatus == PollStatus.CLOSED) throw Exception("Poll closed for event ID: $eventId")
            val user = userService.findUserById(userId)
            val poll = pollService.addVotesFromEvent(user, eventId, ballot)
            countVotes(eventId)
            println(poll.ballot)
            poll
        } catch (e: NotFoundException) {
            throw NoSuchElementException(e.message)
        }
    }

    fun countVotes(eventId: Long) {
        try {
            val event = findEventById(eventId)
            val votes: MutableMap<EventType, Int> = mutableMapOf()

            event.userPolls.forEach {

                for (i in 0 until min(it.ballot.size, 3)) {
                    
                }

                if (it.ballot.isNotEmpty()) {
                    var a = votes.getOrDefault(it.ballot[0], 0)
                    a += 3
                    votes[it.ballot[0]!!] = a
                }
            }
            event.votes = votes
            eventRepo.save(event)
        } catch (e: NoSuchElementException) {
            throw NoSuchElementException(e.message)
        }
    }
}