package com.example.hack_for_change_backend.service

import com.example.hack_for_change_backend.model.Organisation
import com.example.hack_for_change_backend.repository.OrganisationRepo
import org.springframework.stereotype.Service

@Service
class OrganisationService(val organisationRepo: OrganisationRepo){

    fun findOrganisationById(id: Long) = organisationRepo.findById(id).orElseThrow { NoSuchElementException("Organisation with ID: $id does not exist") }

    fun findAll() = organisationRepo.findAll()

    fun addNewOrganisation(organisation: Organisation): Organisation {
        organisationRepo.save(organisation)
        return organisation
    }

//    lateinit var organisationServiceManager: List<Organisation>

    //    fun createOrganisation(organisation: Organisation): ResponseEntity<Organisation> {
//        val organisationManager = (organisationServiceManager.find { organisationList -> organisationList == organisation })
//        if (organisationManager != null) {
//            throw IllegalStateException("[ORGANISATION] $organisation ALREADY EXISTS")
//        }
//        organisationRepo.save(organisation)
//        return ResponseEntity.ok(organisation)
//    }

    fun updateOrganisation(organisationID: Long, organisationDetails: Organisation): Organisation {
        return try {
            val organisation = findOrganisationById(organisationID)
            organisation.email = organisationDetails.email
            organisation.name = organisationDetails.name
            organisation.events = organisationDetails.events
            organisation.phoneNo = organisationDetails.phoneNo
            organisation.employees = organisationDetails.employees
            organisationRepo.save(organisation)
        } catch (e: NoSuchElementException) {
            throw NoSuchElementException(e.message)
        }
    }

    fun deleteOrganisation(organisationID: Long) {
        try {
            organisationRepo.delete(findOrganisationById(organisationID))
        } catch (e: NoSuchElementException) {
            throw NoSuchElementException(e.message)
        }
    }
}