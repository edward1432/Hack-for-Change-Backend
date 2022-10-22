package com.example.hack_for_change_backend.controller.loginSignUp

import org.springframework.security.core.GrantedAuthority

class SignInResponse(var email: String?, val authorities: Collection<GrantedAuthority>) {

}