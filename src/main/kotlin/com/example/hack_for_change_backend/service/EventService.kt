package com.example.hack_for_change_backend.service

import com.example.hack_for_change_backend.model.Event
import com.example.hack_for_change_backend.model.enums.EventStatus
import com.example.hack_for_change_backend.model.enums.EventType
import com.example.hack_for_change_backend.repository.EventRepo
import org.springframework.stereotype.Service

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

}