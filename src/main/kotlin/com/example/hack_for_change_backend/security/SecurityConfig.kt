package com.example.hack_for_change_backend.security

import com.example.hack_for_change_backend.service.UserService
import lombok.AllArgsConstructor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
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
            .antMatchers("/api/v*/registration/**").permitAll()
            //=================USER ENDPOINTS=================
//            .antMatchers("/**").permitAll()
            .antMatchers(HttpMethod.GET, "/**").authenticated()
            .antMatchers(HttpMethod.PATCH, "/**").authenticated()
            .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
            .antMatchers(HttpMethod.GET, "/users/loginUser/**").permitAll()
//            .antMatchers(HttpMethod.GET, "/organisations/findAll").permitAll()
//            .antMatchers(HttpMethod.OPTIONS, "/organisations/findAll").permitAll()
//            .antMatchers(HttpMethod.GET, "/users/loginUser").hasAnyRole("ADMIN", "USER")
//            .antMatchers(HttpMethod.POST, "/addUser").permitAll()
//            .antMatchers(HttpMethod.GET, "/findAll").hasAnyRole("ADMIN", "USER")
//            .antMatchers(HttpMethod.GET, "/findById/**").hasRole("ADMIN")
//            .antMatchers(HttpMethod.PUT, "/updateUser/**").hasAnyRole("ADMIN", "USER")
//            .antMatchers(HttpMethod.DELETE, "/deleteUser/**").hasRole("ADMIN")
//            .antMatchers(HttpMethod.PATCH, "/users/patch/**").hasAnyRole("ADMIN", "USER")
//            //=================POST ENDPOINTS=================
//            .antMatchers(HttpMethod.GET, "/findPosts").hasAnyRole("ADMIN", "USER")
//            .antMatchers(HttpMethod.GET, "/findPostById/**").hasRole("ADMIN")
//            .antMatchers(HttpMethod.POST, "/addPost").hasAnyRole("ADMIN", "USER")
//            .antMatchers(HttpMethod.PUT, "/editPost/**").hasAnyRole("ADMIN", "USER")
//            .antMatchers(HttpMethod.DELETE, "/deletePost/**").hasAnyRole("ADMIN", "USER")
//            //=================VENUE ENDPOINTS=================
//            .antMatchers(HttpMethod.GET, "/findAllVenues").hasAnyRole("ADMIN", "USER")
//            .antMatchers(HttpMethod.GET, "/findVenueById/**").hasRole("ADMIN")
//            .antMatchers(HttpMethod.POST, "/addVenue").hasAnyRole("ADMIN", "USER")
//            .antMatchers(HttpMethod.PUT, "/updateVenue/**").hasAnyRole("ADMIN", "USER")
//            .antMatchers(HttpMethod.DELETE, "/deleteVenue/**").hasAnyRole("ADMIN", "USER")

//            //=================EVENT ENDPOINTS=================
//            .antMatchers(HttpMethod.GET, "/findAllEvents").hasAnyRole("ADMIN", "USER")
//            .antMatchers(HttpMethod.GET, "/findByEventTypeIs").hasAnyRole("ADMIN", "USER")
//            .antMatchers(HttpMethod.GET, "/findEventById/**").hasRole("ADMIN")
//            .antMatchers(HttpMethod.POST, "/addEvent").hasAnyRole("ADMIN", "USER")
//            .antMatchers(HttpMethod.PUT, "/updateEvent/**").hasAnyRole("ADMIN", "USER")
//            .antMatchers(HttpMethod.PUT, "/addEmployee/**").hasAnyRole("ADMIN", "USER")
//            .antMatchers(HttpMethod.DELETE, "/deleteEvent/**").hasAnyRole("ADMIN", "USER")
//            //=================ORGANISATION ENDPOINTS=================
//            .antMatchers(HttpMethod.GET, "/findAllOrganisations").hasAnyRole("ADMIN", "USER")
//            .antMatchers(HttpMethod.GET, "/findOrganisationById/**").hasRole("ADMIN")
//            .antMatchers(HttpMethod.POST, "/addOrganisation").hasAnyRole("ADMIN")
//            .antMatchers(HttpMethod.PUT, "/updateOrganisation/**").hasAnyRole("ADMIN")
//            .antMatchers(HttpMethod.DELETE, "/deleteOrganisation/**").hasAnyRole("ADMIN")
            .anyRequest()
            .authenticated().and()
            .httpBasic().and()
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