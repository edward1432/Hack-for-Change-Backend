package com.example.hack_for_change_backend.repository

import com.example.hack_for_change_backend.model.Poll
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PollRepo : JpaRepository<Poll, Long> {

    companion object
}