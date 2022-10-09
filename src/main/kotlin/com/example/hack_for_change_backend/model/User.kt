package com.example.hack_for_change_backend.model

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
@Table(name = "user", uniqueConstraints = [UniqueConstraint(columnNames = ["email"])])
class User {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val uniqueId: Long? = null

    @Column(name = "first_name")
    var firstName: String? = null
    @Column(name = "last_name")
    var lastName: String? = null
    var email: String? = null
    var password: String? = null

    @ManyToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @JoinTable(
        name = "users_roles",
        joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id")]
    )
    var roles: Collection<Role>? = null
    
    constructor() {}
    constructor(
        firstName: String?, lastName: String?, email: String?,
        password: String?, roles: Collection<Role>?
    ) : super() {
        this.firstName = firstName
        this.lastName = lastName
        this.email = email
        this.password = password
        this.roles = roles
    }
    @ManyToOne
    @JoinColumn(name = "organisation_id")
    var organisation: Organisation? = null

    @JsonIgnore
    @ManyToMany
    @JoinTable(
        name = "employee_event_mapper",
        joinColumns = [JoinColumn(name = "employee_id")],
        inverseJoinColumns = [JoinColumn(name = "event_id")]
    )
    val events: List<Event> = listOf()


}