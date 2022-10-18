package com.example.hack_for_change_backend.model

import javax.persistence.*

@Entity
@Table (name = "polls")
data class Poll (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var uniqueID: Long,

    @OneToOne
    @JoinColumn(name = "event_id")
    var event: Event,

//    @ManyToMany(cascade = [CascadeType.ALL])
//    @JoinTable(
//        name = "user_poll_mapper",
//        joinColumns = [JoinColumn(name = "poll_id")],
//        inverseJoinColumns = [JoinColumn(name = "user_id")]
//    )
//    val voters: Map<User, Ballot> = mutableMapOf(),

    @OneToMany
    val votes: MutableSet<Ballot>
        )