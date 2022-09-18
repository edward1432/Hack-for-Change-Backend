package com.example.hack_for_change_backend.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "user")
data class User (
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    val uniqueId: Long,
    var userName: String,
    @ManyToOne @JoinColumn(name = "organisation_id")
    var organisation: Organisation
)