package com.example.hack_for_change_backend.model

import com.example.hack_for_change_backend.model.enums.EventType
import com.fasterxml.jackson.annotation.JsonIgnore
import java.util.Date
import javax.persistence.*
import kotlin.jvm.Transient

//add mapped by components on line 18a nd 22
@Entity
@Table(name = "events")
data class Event (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var uniqueId: Long,
    var location: String,
    var startDateTime: Date,
    var endDateTime: Date,


    @ManyToOne
    @JoinColumn(name = "organisation_id")
    var organisation: Organisation,

    @JsonIgnore
    @ManyToMany (mappedBy = "events")
    var venues: MutableList<Venue>,

    @Enumerated(value = EnumType.STRING)
    var eventType: EventType?,

    @JsonIgnore
    @ManyToMany (mappedBy = "events")
    val users: MutableList<User> = mutableListOf(),

    @Transient
    val votes: MutableMap<User, Ballot?> = mutableMapOf(),

    @OneToOne
    @JoinColumn
    val poll: Poll
        )