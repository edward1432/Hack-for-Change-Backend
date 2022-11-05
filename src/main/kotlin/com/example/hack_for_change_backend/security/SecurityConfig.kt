package com.example.hack_for_change_backend.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
class SecurityConfig : WebSecurityConfigurerAdapter() {

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

//    @Throws(Exception::class)
//    override fun configure(auth: AuthenticationManagerBuilder) {
//        auth.authenticationProvider(daoAuthenticationProvider())
//    }
//
//    @Bean
//    fun daoAuthenticationProvider(): DaoAuthenticationProvider? {
//        val provider = DaoAuthenticationProvider()
//        provider.setPasswordEncoder(bCryptPasswordEncoder)
//        provider.setUserDetailsService(appUserService)
//        return provider
//    }


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
//
//    @Throws(Exception::class)
//    @Override
//    override fun configure(http: HttpSecurity) {
//        http.httpBasic()
//            .and()
//            .authorizeRequests()
////            .antMatchers(HttpMethod.GET, "/").hasRole("ADMIN")
//            .antMatchers("/users/**").permitAll()
//            .antMatchers(HttpMethod.POST, "/users/addUser/**").hasAnyAuthority("ADMIN", "USER")
////            .antMatchers(HttpMethod.PUT, "//**").hasRole("ADMIN")
////            .antMatchers(HttpMethod.DELETE, "//**").hasRole("ADMIN")
////            .antMatchers(HttpMethod.GET, "//**").hasAnyRole("ADMIN", "USER")
//            .and()
//            .csrf().disable()
//            .formLogin().disable()
//    }

}