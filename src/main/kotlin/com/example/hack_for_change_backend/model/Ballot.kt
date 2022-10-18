package com.example.hack_for_change_backend.model

import com.example.hack_for_change_backend.model.enums.EventType
import javax.persistence.*

@Entity
@Table(name = "ballots")
data class Ballot (
//    val choice: EventType
    @Id
    @Column(name = "ballot_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var uniqueId: Long,
    @ManyToOne
    @JoinColumn(name = "user_id")
    var user: User,
    @ManyToOne
    @JoinColumn(name = "poll_id")
    var poll: Poll,
    @Enumerated(value = EnumType.STRING)
    var choice: EventType
        )