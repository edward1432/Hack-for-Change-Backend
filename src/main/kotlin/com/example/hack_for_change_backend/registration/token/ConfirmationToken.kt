package com.example.hack_for_change_backend.registration.token

import com.example.hack_for_change_backend.model.User
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter
import java.time.LocalDateTime
import javax.persistence.*


@Getter
@Setter
@NoArgsConstructor
@Entity
class ConfirmationToken(
    @field:Column(nullable = false) private val token: String,
    @field:Column(nullable = false) private val createdAt: LocalDateTime,
    @field:Column(nullable = false) private val expiresAt: LocalDateTime,
    user: User
) {
    @SequenceGenerator(
        name = "confirmation_token_sequence",
        sequenceName = "confirmation_token_sequence",
        allocationSize = 1
    )
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "confirmation_token_sequence")
    private val id: Long? = null
    private val confirmedAt: LocalDateTime? = null

    @ManyToOne
    @JoinColumn(nullable = false, name = "app_user_id")
    private val user: User

    init {
        this.user = user
    }
}