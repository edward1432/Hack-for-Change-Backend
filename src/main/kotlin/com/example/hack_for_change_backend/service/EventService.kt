package com.example.hack_for_change_backend.service

import com.example.hack_for_change_backend.model.Ballot
import com.example.hack_for_change_backend.model.Event
import com.example.hack_for_change_backend.model.User
import com.example.hack_for_change_backend.model.enums.EventType
import com.example.hack_for_change_backend.repository.EventRepo
import org.springframework.stereotype.Service

@Service
class EventService(val eventRepo: EventRepo, val organisationService: OrganisationService, val userService: UserService) {

    fun findEventById(id: Long) = eventRepo.findById(id).orElseThrow { NoSuchElementException("Event with ID: $id does not exist") }

    fun findEventByType(eventType: EventType): List<Event> = eventRepo.findByEventTypeIs(eventType)

    fun findAll() = eventRepo.findAll()

    fun addNewEvent(event: Event): Event {
        return try {
            organisationService.findOrganisationById(event.organisation.uniqueId)
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

    fun addEmployeeToEvent(eventID: Long, employeeID: Long) {
        try {
            val event = findEventById(eventID)
            event.users.add(userService.findUserById(employeeID))
            eventRepo.save(event)
        } catch (e: NoSuchElementException) {
            throw NoSuchElementException(e.message)
        }
    }

    private fun isUserInEvent(user: User, event: Event): Boolean = event.users.contains(user)
    fun castBallot(eventId: Long, userId: Long, choice: EventType) {
        try {
            val event = findEventById(eventId)
            val user = userService.findUserById(userId)
            if (isUserInEvent(user, event)) {
                event.votes[user] = Ballot(choice)
            } else {
                throw NoSuchElementException("User ${user.uniqueId} not registered for this event")
            }
            eventRepo.save(event)
        } catch (e: NoSuchElementException) {
            throw NoSuchElementException(e.message)
        }
    }
}