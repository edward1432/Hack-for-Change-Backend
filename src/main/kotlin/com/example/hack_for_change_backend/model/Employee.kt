package com.example.hack_for_change_backend.model

//import javax.persistence.Column
//import javax.persistence.Entity
//import javax.persistence.GeneratedValue
//import javax.persistence.GenerationType
//import javax.persistence.Id
//import javax.persistence.JoinColumn
//import javax.persistence.ManyToOne
//import javax.persistence.Table
import javax.persistence.*

@Entity
@Table(name = "employees")
data class Employee (
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val uniqueId: Long,
    @Column(name = "user_name")
    var userName: String,
    @ManyToOne
    @JoinColumn(name = "organisation_id")
    var organisation: Organisation,
    @ManyToMany
    @JoinTable(
        name = "employee_event_mapper",
        joinColumns = [JoinColumn(name = "employee_id")],
    inverseJoinColumns = [JoinColumn(name = "event_id")]
        )
    val events: List<Event> = listOf()
)