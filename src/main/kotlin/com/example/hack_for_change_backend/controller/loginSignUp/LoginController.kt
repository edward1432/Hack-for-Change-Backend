package com.example.hack_for_change_backend.controller.loginSignUp

import com.example.hack_for_change_backend.model.User
import com.example.hack_for_change_backend.security.JwtProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.stream.Collectors
import javax.servlet.http.HttpServletResponse
import javax.validation.Valid

@RestController
@RequestMapping("/signInSection")
class LoginController() {
    @Autowired
    lateinit var authenticationManager: AuthenticationManager

    @Autowired
    lateinit var jwtProvider: JwtProvider
    @PostMapping("/login")
    fun loginUser(@Valid @RequestBody user: User, response: HttpServletResponse): ResponseEntity<*> {

        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(user.email, user.password)
        )
        SecurityContextHolder.getContext().authentication = authentication
        val jwt: String = jwtProvider.generateJwtToken(user.email!!)
        val authorities: List<GrantedAuthority> = user.roles!!.stream().map { role -> SimpleGrantedAuthority(role.roleName) }
            .collect(Collectors.toList<GrantedAuthority>())
        return ResponseEntity.ok(SignInResponse(user.email, authorities))
    }
}