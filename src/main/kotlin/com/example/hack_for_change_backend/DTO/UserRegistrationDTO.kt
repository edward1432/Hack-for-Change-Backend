package com.example.hack_for_change_backend.DTO

class UserRegistrationDto {
    var firstName: String? = null
    var lastName: String? = null
    var email: String? = null
    var password: String? = null

    constructor() {}
    constructor(
        firstName: String?,
        lastName: String?, email: String?, password: String?
    ) : super() {
        this.firstName = firstName
        this.lastName = lastName
        this.email = email
        this.password = password
    }
}