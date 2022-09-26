package com.example.hack_for_change_backend.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import javax.persistence.*

@Entity
@Table(name = "organisation")
data class Organisation (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var uniqueId: Long,
    @Column(name = "name")
    var name: String,
    var email: String,
    var phoneNo: String,

    @JsonIgnoreProperties("organisation")
    @OneToMany(mappedBy = "organisation", cascade = [CascadeType.ALL])
    var enjoyers: MutableList<User>,

    @JsonIgnoreProperties("organisation")
    @OneToMany(mappedBy = "organisation", cascade = [CascadeType.ALL])
    var events: MutableList<Event>
        )