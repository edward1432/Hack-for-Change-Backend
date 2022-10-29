package com.example.hack_for_change_backend.model

import lombok.Data
import org.hibernate.annotations.GenericGenerator

import javax.persistence.*

@Data
@Entity
@Table(name = "role")
class Role {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(name = "role_name")
    var roleName: String? = null

    @Column(name = "description")
    var description: String? = null
}