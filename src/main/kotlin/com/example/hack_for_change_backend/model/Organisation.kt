package com.example.hack_for_change_backend.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import javax.persistence.*

@Entity
@Table(name = "organisation")
data class Organisation (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val uniqueId: Long,
    @Column(name = "name")
    val name: String,
    val email: String,
    val phoneNo: String,

    @JsonIgnoreProperties("organisation")
    @OneToMany(mappedBy = "organisation", cascade = [CascadeType.ALL])
    val users: List<User>,

    @JsonIgnoreProperties("organisation")
    @OneToMany(mappedBy = "organisation", cascade = [CascadeType.ALL])
    val events: List<Event>
        )