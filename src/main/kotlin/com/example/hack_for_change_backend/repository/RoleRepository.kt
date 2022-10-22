package com.example.hack_for_change_backend.repository


import com.example.hack_for_change_backend.model.Role
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.query.Param

interface RoleRepository : JpaRepository<Role, String>{
    fun findByName(@Param("name") name: String): Role
}