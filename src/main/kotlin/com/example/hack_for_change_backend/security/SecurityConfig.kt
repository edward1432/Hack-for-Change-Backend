package com.example.hack_for_change_backend.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
class SecurityConfig : WebSecurityConfigurerAdapter() {

    @Bean
    fun encoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.inMemoryAuthentication()
            .withUser("admin")
            .password(encoder().encode("admin"))
            .roles("USER", "ADMIN")
            .and()
            .withUser("user")
            .password(encoder().encode("user"))
            .roles("USER")
    }

//    @Throws(Exception::class)
//    override fun configure(http: HttpSecurity) {
//        http.httpBasic()
//            .and()
//            .authorizeRequests()
//            .antMatchers(HttpMethod.GET, "/").hasRole("ADMIN")
//            .antMatchers(HttpMethod.POST, "//**").hasRole("ADMIN")
//            .antMatchers(HttpMethod.PUT, "//**").hasRole("ADMIN")
//            .antMatchers(HttpMethod.DELETE, "//**").hasRole("ADMIN")
//            .antMatchers(HttpMethod.GET, "//**").hasAnyRole("ADMIN", "USER")
//            .and()
//            .csrf().disable()
//            .formLogin().disable()
//    }

}