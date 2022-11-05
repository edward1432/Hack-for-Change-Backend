package com.example.hack_for_change_backend.registration

import com.example.hack_for_change_backend.model.enums.UserRoles
import lombok.AllArgsConstructor
import lombok.EqualsAndHashCode
import lombok.Getter
import lombok.ToString


//@Getter
//@AllArgsConstructor
//@EqualsAndHashCode
//@ToString
data class RegistrationRequest (
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String
)