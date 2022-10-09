package com.example.hack_for_change_backend.service


import com.example.hack_for_change_backend.DTO.UserRegistrationDto
import com.example.hack_for_change_backend.model.Role
import com.example.hack_for_change_backend.model.User
import com.example.hack_for_change_backend.repository.UserRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.util.*
import java.util.function.Function
import java.util.stream.Collectors


@Service
class UserServiceImpl(private val userRepository: UserRepo) : UserDataService {
    @Autowired
    private val passwordEncoder: BCryptPasswordEncoder? = null
    override fun save(registrationDto: UserRegistrationDto?): User? {
        val user = User(
            registrationDto!!.firstName,
            registrationDto.lastName, registrationDto.email,
            passwordEncoder!!.encode(registrationDto.password),
            Arrays.asList(Role("ROLE_USER"))
        )
        return userRepository.save(user)
    }

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByEmail(username)
            ?: throw UsernameNotFoundException("Invalid username or password.")
        return org.springframework.security.core.userdetails.User(
            user.email, user.password,
            mapRolesToAuthorities(user.roles!!)
        )
    }

    private fun mapRolesToAuthorities(roles: Collection<Role>): Collection<GrantedAuthority?> {
        return roles.stream().map(Function<Role, SimpleGrantedAuthority?> { role: Role ->
            SimpleGrantedAuthority(
                role.name
            )
        }).collect(Collectors.toList())
    }

    override val all: List<User?>?
        get() = userRepository.findAll()

}