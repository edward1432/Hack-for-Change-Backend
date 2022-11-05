package com.example.hack_for_change_backend.model

import com.example.hack_for_change_backend.model.enums.UserRoles
import com.fasterxml.jackson.annotation.JsonIgnore
import lombok.Builder
import lombok.Data
import org.hibernate.validator.constraints.Length
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*
import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull


@Data
@Entity
@Builder
@Table(name = "users")
class User(
    @Column(name = "first_name")
    var firstName: String,
    @Column(name = "last_name")
    var lastName: String,
    @Column(name = "email_address")
    @Email(message = "Please enter a valid Email")
    @NotEmpty(message = "Please enter a valid email")
    var email: String,
//    @NotEmpty(message = "Please enter your password")
//    @NotNull(message = "A Password is required")
    internal var password: String,
    @Enumerated(EnumType.STRING)
    var role: UserRoles
) : UserDetails {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var uniqueId: Long = 0

    //    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(name = "user_role", joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "uniqueId")],
//        inverseJoinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id")]
//    )
//    var roles: MutableSet<Role>? = null,

    @ManyToOne
    @JoinColumn(name = "organisation_id")
    var organisation: Organisation? = null

    @JsonIgnore
    @ManyToMany(cascade = [CascadeType.ALL])
    @JoinTable(
        name = "user_event_mapper",
        joinColumns = [JoinColumn(name = "unique_id")],
        inverseJoinColumns = [JoinColumn(name = "event_id")]
    )
    val events: List<Event> = mutableListOf()
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        TODO("Not yet implemented")
    }

    override fun getPassword(): String {
        TODO("Not yet implemented")
    }

    override fun getUsername(): String {
        TODO("Not yet implemented")
    }

    override fun isAccountNonExpired(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isAccountNonLocked(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isCredentialsNonExpired(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isEnabled(): Boolean {
        TODO("Not yet implemented")
    }

}
