package com.example.hack_for_change_backend.model

//import javax.persistence.Column
//import javax.persistence.Entity
//import javax.persistence.GeneratedValue
//import javax.persistence.GenerationType
//import javax.persistence.Id
//import javax.persistence.JoinColumn
//import javax.persistence.ManyToOne
//import javax.persistence.Table
import java.util.spi.CalendarDataProvider
import javax.persistence.*

@Entity
@Table(name = "enjoyer")
data class User (
    @Id
    @Column(name = "id", nullable = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val uniqueId: Long,
    @Column(name = "user_name")
    var userName: String,
    @ManyToOne
    @JoinColumn(name = "organisation_id")
    var organisation: Organisation,
    @ManyToMany
    @JoinTable(name = "enjoyer_event_mapper")
    var events: List<Event>
)