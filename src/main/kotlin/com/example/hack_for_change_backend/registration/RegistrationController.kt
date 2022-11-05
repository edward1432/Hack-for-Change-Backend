package com.example.hack_for_change_backend.registration

import lombok.AllArgsConstructor
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping(path = ["api/v1/registration"])
@AllArgsConstructor
class RegistrationController {
    private val registrationService: RegistrationService? = null
    @PostMapping
    fun register(@RequestBody request: RegistrationRequest): String {
        return registrationService!!.register(request)
    }

    @GetMapping(path = ["confirm"])
    fun confirm(@RequestParam("token") token: String?): String {
        return registrationService!!.confirmToken(token)
    }
}