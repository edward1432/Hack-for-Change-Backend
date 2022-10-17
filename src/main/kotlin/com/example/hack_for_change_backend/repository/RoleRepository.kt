package com.example.hack_for_change_backend.repository


import com.example.hack_for_change_backend.model.Role
import org.springframework.data.jpa.repository.JpaRepository

interface RoleRepository : JpaRepository<Role, String>