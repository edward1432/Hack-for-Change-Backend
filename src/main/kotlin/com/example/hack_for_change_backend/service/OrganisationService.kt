package com.example.hack_for_change_backend.service

import com.example.hack_for_change_backend.model.Organisation
import com.example.hack_for_change_backend.repository.OrganisationRepo
import org.springframework.stereotype.Service

@Service
class OrganisationService(val organisationRepo: OrganisationRepo){

    fun findOrganisationById(id: Long): Organisation = organisationRepo.findById(id).orElseThrow { NoSuchElementException("Organisation with ID: $id does not exist") }

    fun findAll(): List<Organisation> = organisationRepo.findAll()

    fun findOrganisationByJoinCode(joinCode: String): Organisation = organisationRepo.findByJoinCodeIs(joinCode) ?:
    throw NoSuchElementException("Organisation with join code: $joinCode does not exist")

    fun addNewOrganisation(organisation: Organisation): Organisation {
        organisationRepo.save(organisation)
        return organisation
    }

    fun updateOrganisation(organisationID: Long, organisationDetails: Organisation): Organisation {
        return try {
            val organisation = findOrganisationById(organisationID)
            organisation.email = organisationDetails.email
            organisation.name = organisationDetails.name
            organisation.events = organisationDetails.events
            organisation.phoneNo = organisationDetails.phoneNo
            organisation.users = organisationDetails.users
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