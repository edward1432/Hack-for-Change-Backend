package com.example.hack_for_change_backend.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.hibernate.annotations.GenericGenerator
import java.util.UUID
import javax.persistence.*

@Entity
@Table(name = "organisations")
data class Organisation (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var uniqueId: Long,
    val joinCode: String,
    @Column(name = "name")
    var name: String,
    var email: String,
    var phoneNo: String,

    @JsonIgnoreProperties("organisation")
    @OneToMany(mappedBy = "organisation", cascade = [CascadeType.PERSIST])
    var users: MutableList<User>,

    @JsonIgnoreProperties("organisation")
    @OneToMany(mappedBy = "organisation", cascade = [CascadeType.PERSIST])
    var events: MutableList<Event>
        )