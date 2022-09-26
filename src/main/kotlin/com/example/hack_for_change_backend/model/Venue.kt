package com.example.hack_for_change_backend.model

import com.example.hack_for_change_backend.model.enums.EventType
import javax.persistence.*
import javax.xml.stream.Location
//add mapped by components on 14
@Entity
@Table(name = "venues")
data class Venue (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val uniqueId: Long,

    @OneToOne
    @JoinColumn(name = "event_id")
    var event: Event,
    var name: String,
    var location: String,

    @Enumerated(value = EnumType.STRING)
    var type: EventType
)