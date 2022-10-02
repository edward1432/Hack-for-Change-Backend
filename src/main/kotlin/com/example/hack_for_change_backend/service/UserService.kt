package com.example.hack_for_change_backend.service

import com.example.hack_for_change_backend.model.Employee
import com.example.hack_for_change_backend.repository.UserRepo
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class UserService(val userRepo: UserRepo) {

    fun findUserById(id: Long): Employee {
        return userRepo.findById(id).orElseThrow {
            NoSuchElementException("[USER] $id NOT FOUND")
        }
    }

    fun findAll(): List<Employee> {
        return userRepo.findAll()
    }

    fun createUser(employee: Employee): ResponseEntity<Employee>{
        userRepo.save(employee)
        return ResponseEntity.ok(employee)
    }


    fun updateUser(userId: Long, employeeDetails: Employee): ResponseEntity<Employee> {
        try {
            val user = findUserById(userId)
            user.userName = employeeDetails.userName
            user.organisation = employeeDetails.organisation
            return ResponseEntity.ok(user)
        } catch (e: NoSuchElementException) {
            throw NoSuchElementException(e.message)
        }
    }

    fun deleteUser(userId: Long): ResponseEntity<HttpStatus>{
        try{
            userRepo.delete(findUserById(userId))
            return ResponseEntity.ok(HttpStatus.OK)
        }catch(e: NoSuchElementException){
            throw NoSuchElementException(e.message)
        }
    }
}
