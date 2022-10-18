package com.example.hack_for_change_backend.repository

import com.example.hack_for_change_backend.model.Ballot
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BallotRepo : JpaRepository<Ballot, Long> {
    companion object
}