package com.example.hack_for_change_backend.repository

import com.example.hack_for_change_backend.model.Organisation
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrganisationRepo : JpaRepository<Organisation, Long> {
    companion object
}