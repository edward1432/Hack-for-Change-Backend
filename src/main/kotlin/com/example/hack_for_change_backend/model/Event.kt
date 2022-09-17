package com.example.hack_for_change_backend.model

import com.example.hack_for_change_backend.model.enums.EventType
import jdk.javadoc.doclet.Taglet.Location
import java.util.Date
import javax.persistence.*

@Entity
@Table(name = "events")
data class Event (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val uniqueId: Long,
    val location: String,
    val date: Date,

    @ManyToOne
    @JoinColumn(name = "organisation_id")
    val organisation: Organisation,

    @OneToMany
    @JoinColumn(name = "venue_id")
    val venues: MutableList<Venue>,
    val eventType: EventType
        )