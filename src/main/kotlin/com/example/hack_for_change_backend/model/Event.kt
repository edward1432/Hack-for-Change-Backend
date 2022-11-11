package com.example.hack_for_change_backend.model

import com.example.hack_for_change_backend.model.enums.EventStatus
import com.example.hack_for_change_backend.model.enums.EventType
import com.example.hack_for_change_backend.model.enums.PollStatus
import com.example.hack_for_change_backend.model.voting.Poll
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonManagedReference
import java.util.Date
import javax.persistence.*

//add mapped by components on line 18a nd 22
@Entity
@Table(name = "events")
data class Event (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var uniqueId: Long,
    var location: String,
    var name: String,
    var startDateTime: Date,
    var endDateTime: Date,
    var description: String?,

    @ManyToOne
    @JoinColumn(name = "organisation_id")
    var organisation: Organisation?,

    @JsonManagedReference
    @OneToMany(mappedBy = "event")
    val userPolls: MutableList<Poll> = mutableListOf(),

    @Enumerated(value = EnumType.STRING)
    var pollStatus: PollStatus = PollStatus.OPEN,

    @JsonIgnore
    @ManyToMany (mappedBy = "events")
    var venues: MutableList<Venue>?,

    @Enumerated(value = EnumType.STRING)
    var eventType: EventType?,

    @ElementCollection
    var votes: MutableMap<EventType, Int>,

//    @JsonIgnore
//    @ManyToMany (mappedBy = "events")
//    val users: MutableList<User> = mutableListOf(),


    @Enumerated(value = EnumType.STRING)
    var status: EventStatus = EventStatus.PROPOSED
        )