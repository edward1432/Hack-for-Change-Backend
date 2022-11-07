package com.example.hack_for_change_backend.model.voting

import com.example.hack_for_change_backend.model.Event
import com.example.hack_for_change_backend.model.User
import com.example.hack_for_change_backend.model.enums.EventType
import com.example.hack_for_change_backend.model.enums.RSVP
import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
@Table(name = "polls")
data class Poll(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val uniqueId: Long,
    @ManyToOne
    val user: User,
    @ManyToOne
    val event: Event,
    @ElementCollection
//    @Access(AccessType.PROPERTY)
    var ballot: MutableList<EventType>,
    @Column(name = "rsvp")
    @Enumerated(value = EnumType.STRING)
    var rsvp: RSVP? = null
)

