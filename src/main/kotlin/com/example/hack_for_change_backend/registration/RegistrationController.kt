package com.example.hack_for_change_backend.registration

import lombok.AllArgsConstructor
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping(path = ["api/v1/registration"])
@AllArgsConstructor
@CrossOrigin(origins = ["http://localhost:3000"])
class RegistrationController (val registrationService: RegistrationService){
//    private val registrationService = RegistrationService()
    @PostMapping
    fun register(@RequestBody request: RegistrationRequest): String {
        return registrationService.register(request)
    }

    @GetMapping("/confirm")
    fun confirm(@RequestParam("token") token: String): String {
        return registrationService.confirmToken(token)
    }
}