//package com.example.hack_for_change_backend.service
//
//import com.example.hack_for_change_backend.model.Ballot
//import com.example.hack_for_change_backend.model.enums.EventType
//import com.example.hack_for_change_backend.repository.BallotRepo
//import org.springframework.stereotype.Service
//
////@Service
////class BallotService (val ballotRepo: BallotRepo, val userService: UserService, val pollService: PollService) {
////
//////    fun castBallot(ballot: Ballot): Ballot {
//////        return try {
//////            pollService.findPollById(ballot.poll.uniqueID)
//////            userService.findUserById(ballot.user.uniqueId)
//////            ballotRepo.save(ballot)
//////            ballot
//////        } catch (e: NoSuchElementException) {
//////            throw NoSuchElementException(e.message)
//////        }
//////    }
////}