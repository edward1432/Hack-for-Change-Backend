package com.example.hack_for_change_backend.service

import com.example.hack_for_change_backend.repository.OrganisationRepo
import org.springframework.stereotype.Service

@Service
class OrganisationService(val organisationRepo: OrganisationRepo){

    fun findOrganisationById(id: Long) = organisationRepo.findById(id).orElseThrow { NoSuchElementException("Organisation with ID: $id does not exist") }

    fun findAll() = organisationRepo.findAll()


}