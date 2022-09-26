package com.example.hack_for_change_backend.repository

import com.example.hack_for_change_backend.model.Organisation
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface OrganisationRepo : JpaRepository<Organisation, Long> {
    companion object

    @Query(value = "SELECT * FROM organisations WHERE id = ?", nativeQuery = true)
    fun findOrganisationById(id: Long): Organisation

    @Query(value = "SELECT * FROM organisations WHERE lower(organisation) LIKE lower(?1)", nativeQuery = true)
    fun findOrganisationByName(@Param("organisations") organisations: String): List<Organisation>

}