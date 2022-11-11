package com.example.hack_for_change_backend.registration.token

import com.example.hack_for_change_backend.model.User
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class ConfirmationToken(
    @Column(nullable = false)
    val token: String,
    @Column(nullable = false)
    val createdAt: LocalDateTime,
    @Column(nullable = false)
    val expiresAt: LocalDateTime,
    user: User
) {
    @SequenceGenerator(
        name = "confirmation_token_sequence",
        sequenceName = "confirmation_token_sequence",
        allocationSize = 1
    )
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "confirmation_token_sequence")
    val id: Long? = null
    val confirmedAt: LocalDateTime? = null

    @ManyToOne
    @JoinColumn(nullable = false, name = "user_id")
    val user: User

    init {
        this.user = user
    }
}