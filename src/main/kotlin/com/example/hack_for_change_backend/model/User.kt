package com.example.hack_for_change_backend.model

import com.example.hack_for_change_backend.model.enums.UserRoles
import com.example.hack_for_change_backend.model.voting.Poll
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import lombok.Builder
import lombok.Data
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*
import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty


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
    //    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(name = "user_role", joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "uniqueId")],
//        inverseJoinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id")]
//    )
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var uniqueId: Long = 0

    @JsonIgnoreProperties("user")
    @OneToMany(mappedBy = "user", cascade = [CascadeType.PERSIST])
    val myPosts: MutableList<Post> = mutableListOf()

//    @JsonIgnore
//    @ManyToMany(cascade = [CascadeType.ALL])
//    @JoinTable(
//        name = "user_event_mapper",
//        joinColumns = [JoinColumn(name = "unique_id")],
//        inverseJoinColumns = [JoinColumn(name = "event_id")]
//    )
//    val events: List<Event> = mutableListOf()

    @JsonIgnoreProperties("user")
    @OneToMany(mappedBy = "user")
    val polls: MutableList<Poll> = mutableListOf()
//    var roles: MutableSet<Role>? = null,

    @ManyToOne
    @JoinColumn(name = "organisation_id")
    var organisation: Organisation? = null

    private var locked: Boolean = false
    private var enabled: Boolean = true

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        val authority = SimpleGrantedAuthority(role.name)
        return Collections.singletonList(authority)
    }

    override fun getPassword(): String = password

    override fun getUsername(): String = email

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = !locked

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = enabled
}
