package com.example.hack_for_change_backend.security

import com.example.hack_for_change_backend.service.UserService
import lombok.AllArgsConstructor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder


@Configuration
@AllArgsConstructor
@EnableWebSecurity
class WebSecurityConfig (val userService: UserService): WebSecurityConfigurerAdapter() {

//    private val userService: UserService = UserService()
    final val bCryptPasswordEncoder = BCryptPasswordEncoder()

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http
            .csrf().disable()
            .authorizeRequests()
            .antMatchers("/api/v*/registration/**")
            .permitAll()
            .anyRequest()
            .authenticated().and()
            .formLogin()
    }

    @Throws(Exception::class)
    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.authenticationProvider(daoAuthenticationProvider())
        auth.inMemoryAuthentication()
            .withUser("admin")
            .password(bCryptPasswordEncoder.encode("admin"))
            .roles("USER", "ADMIN")
            .and()
            .withUser("user")
            .password(bCryptPasswordEncoder.encode("user"))
            .roles("USER")
    }

    @Bean
    fun daoAuthenticationProvider(): DaoAuthenticationProvider {
        val provider = DaoAuthenticationProvider()
        provider.setPasswordEncoder(bCryptPasswordEncoder)
        provider.setUserDetailsService(userService)
        return provider
    }
}