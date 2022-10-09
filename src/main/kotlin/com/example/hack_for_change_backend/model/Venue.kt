package com.example.hack_for_change_backend.model

import com.example.hack_for_change_backend.model.enums.EventType
import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*
import javax.xml.stream.Location
//add mapped by components on 14
@Entity
@Table(name = "venues")
data class Venue (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val uniqueId: Long,

    @JsonIgnore
    @ManyToMany(cascade = [CascadeType.ALL])
    @JoinTable(
        name = "venue_event_mapper",
        joinColumns = [JoinColumn(name = "venue_id")],
        inverseJoinColumns = [JoinColumn(name = "event_id")]
    )
    val events: List<Event> = listOf(),
    var name: String,
    var location: String,

    @Enumerated(value = EnumType.STRING)
    var type: EventType
)