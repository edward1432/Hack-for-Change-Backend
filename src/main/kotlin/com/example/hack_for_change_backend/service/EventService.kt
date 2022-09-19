package com.example.hack_for_change_backend.service

import com.example.hack_for_change_backend.model.Event
import com.example.hack_for_change_backend.repository.EventRepo
import org.springframework.stereotype.Service

@Service
class EventService(val eventRepo: EventRepo, val organisationService: OrganisationService) {

    fun findEventById(id: Long) = eventRepo.findById(id).orElseThrow { NoSuchElementException("Event with ID: $id does not exist") }

    fun findAll() = eventRepo.findAll()

    fun addNewEvent(event: Event): Event {
        return try {
            organisationService.findOrganisationById(event.organisation.uniqueId)
            eventRepo.save(event)
            event
        } catch (e: NoSuchElementException) {
            throw e
        }
    }

    // ===
    fun updateEvent(eventId: Long, eventDetails: Event): Event {
        try {
            val event = findEventById(eventId)
            event.eventType = eventDetails.eventType
            event.organisation = eventDetails.organisation
            event.date = eventDetails.date
            event.venues = eventDetails.venues
            event.location = eventDetails.location
            eventRepo.save(event)
            return event
        } catch (e: NoSuchElementException){
            throw e
        }
    }

    fun deleteEvent(eventId: Long) {
        try {
            eventRepo.delete(findEventById(eventId))
        } catch (e: NoSuchElementException) {
            throw e
        }
    }

}