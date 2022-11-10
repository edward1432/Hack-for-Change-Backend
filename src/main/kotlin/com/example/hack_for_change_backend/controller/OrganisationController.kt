package com.example.hack_for_change_backend.controller

import com.example.hack_for_change_backend.model.Organisation

import com.example.hack_for_change_backend.service.OrganisationService
import com.fasterxml.jackson.databind.JsonNode
import com.github.fge.jsonpatch.JsonPatch
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

@RestController
@CrossOrigin(origins = ["*"])
@RequestMapping("/organisations")
class OrganisationController(private val organisationService: OrganisationService) {

    @GetMapping("/findAll")
    fun getAllOrganisations(): ResponseEntity<List<Organisation
    >> = ResponseEntity.ok(organisationService.findAll())

    @GetMapping("/findById/{id}")
    fun findOrganisationById(@PathVariable("id") organisationId: Long): ResponseEntity<Organisation> {
        return try {
            ResponseEntity.ok(organisationService.findOrganisationById(organisationId))
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message)
        }
    }

    @PostMapping("/addOrganisation")
    fun addOrganisation(@RequestBody organisation: Organisation
    ): ResponseEntity<Organisation> {
        return try {
            ResponseEntity.ok().body(organisationService.addNewOrganisation
                (organisation))
        } catch (e: NoSuchElementException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message)
        }
    }

    @PutMapping("/updateOrganisation" + "/{id}")
    fun updateOrganisation(@PathVariable("id") organisationId: Long, @RequestBody updatedOrganisation
    : Organisation
    ): ResponseEntity<Organisation
            > {
        return try {
            ResponseEntity.ok().body(organisationService.updateOrganisation
                (organisationId, updatedOrganisation
            ))
        } catch (e: NoSuchElementException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message)
        }
    }

//    @PatchMapping("/update/{id}", consumes = ["application/json-patch+json"])
//    fun updateOrganisationEmail(@PathVariable("id") organisationId: Long, @RequestBody patch: JsonPatch): ResponseEntity<Organisation> {
//        return try {
//            val organisation = organisationService.findOrganisationById(organisationId)
//            val organisationPatched = applyPatchTo
//        }
//    }
//
//    @PatchMapping("/update/{id}")
//    fun updateOrg(@PathVariable("id") organisationId: Long, @RequestBody objectMap: Map<Any, Any>) {
//
//    }

//    @DeleteMapping("/deleteOrganisation/{id}")
//    fun deleteOrganisation(@PathVariable("id") organisationId: Long): ResponseEntity<HttpStatus> {
//        return try {
//            organisationService.deleteOrganisation(organisationId)
//            ResponseEntity.ok().body(HttpStatus.OK)
//        } catch (e: NoSuchElementException) {
//            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message)
//        }
//    }
}