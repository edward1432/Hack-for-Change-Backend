package com.example.hack_for_change_backend.registration

import lombok.AllArgsConstructor
import lombok.EqualsAndHashCode
import lombok.Getter
import lombok.ToString


@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
class RegistrationRequest {
    private val firstName: String? = null
    private val lastName: String? = null
    val email: String? = null
    private val password: String? = null
}