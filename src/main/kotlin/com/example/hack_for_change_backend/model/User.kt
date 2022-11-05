package com.example.hack_for_change_backend.model

import com.example.hack_for_change_backend.model.enums.UserRoles
import com.fasterxml.jackson.annotation.JsonIgnore
import lombok.Builder
import lombok.Data
import org.hibernate.validator.constraints.Length
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import java.util.*
import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull


@Data
@Entity
@Builder
@Table(name = "users")
data class User (
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var uniqueId: Long,

    @Column(name = "email_address")
    @Email(message = "Please enter a valid Email")
    @NotEmpty(message = "Please enter a valid email")
    internal var email: String?,

    @Length(min = 8, message = "Your password must have at least 8 characters")
    @NotEmpty(message = "Please enter your password")
    @NotNull(message = "A Password is required")
    internal var password: String?,
//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(name = "user_role", joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "uniqueId")],
//        inverseJoinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id")]
//    )
//    var roles: MutableSet<Role>? = null,
    @Enumerated(EnumType.STRING)
    var role: UserRoles?,

    @ManyToOne
    @JoinColumn(name = "organisation_id")
    var organisation: Organisation? = null,

    @JsonIgnore
    @ManyToMany(cascade = [CascadeType.ALL])
    @JoinTable(
        name = "user_event_mapper",
        joinColumns = [JoinColumn(name = "unique_id")],
        inverseJoinColumns = [JoinColumn(name = "event_id")]
    )
    val events: List<Event> = listOf()
    )

fun user(
    email: String,
    password: String,
    userRoles: UserRoles
) {
    this.email = email
    this.password = password
    this.userRoles = userRoles
}
fun getAuthorities(): Collection<GrantedAuthority?>? {
    val authority = SimpleGrantedAuthority(userRoles.name())
    return Collections.singletonList(authority)
}
