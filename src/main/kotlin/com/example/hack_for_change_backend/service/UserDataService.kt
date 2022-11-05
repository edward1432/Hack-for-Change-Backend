package com.example.hack_for_change_backend.service


import com.example.hack_for_change_backend.model.User
import com.example.hack_for_change_backend.repository.UserRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

import java.util.ArrayList

@Component
class UserDataService @Autowired
constructor(private val userRepository: UserRepo, val passwordEncoder: PasswordEncoder) : UserDetailsService {



    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(s: String): UserDetails {
        val user = userRepository.findByEmail(s)
            ?: throw UsernameNotFoundException(String.format("The username %s doesn't exist", s))

        val authorities = ArrayList<GrantedAuthority>()
//        user.roles!!.forEach { role -> authorities.add(SimpleGrantedAuthority(role.roleName)) }

        return org.springframework.security.core.userdetails.User(user.email, user.password, authorities)
    }

    }
