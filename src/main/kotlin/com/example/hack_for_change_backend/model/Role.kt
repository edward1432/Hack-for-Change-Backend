package com.example.hack_for_change_backend.model

import javax.persistence.*

@Entity
@Table(name = "role")
class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
    var name: String? = null

    constructor() {}
    constructor(name: String?) : super() {
        this.name = name
    }
}