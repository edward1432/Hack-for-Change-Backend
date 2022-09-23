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
@Table(name = "enjoyers")
data class User (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val uniqueId: Long,
    @Column(name = "user_name")
    var userName: String,
    @ManyToOne
    @JoinColumn(name = "organisation_id")
    var organisation: Organisation
)