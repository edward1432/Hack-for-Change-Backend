package com.example.hack_for_change_backend.model

import com.example.hack_for_change_backend.model.enums.EventType
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
    var date: Date,

    @ManyToOne
    @JoinColumn(name = "organisation_id")
    var organisation: Organisation,

    @OneToMany
    @JoinColumn(name = "venue_id")
    var venues: MutableList<Venue>,

    @Enumerated(value = EnumType.STRING)
    var eventType: EventType,

    @ManyToMany (mappedBy = "events")
    val enjoyers: List<Enjoyer> = listOf()
        )