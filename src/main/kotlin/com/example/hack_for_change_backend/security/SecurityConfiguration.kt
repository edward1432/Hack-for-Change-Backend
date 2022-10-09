package com.example.hack_for_change_backend.security
import com.example.hack_for_change_backend.service.UserDataService

import org.springframework.http.HttpMethod
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import kotlin.Throws
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import java.lang.Exception

@Configuration
@EnableWebSecurity
class SecurityConfiguration : WebSecurityConfigurerAdapter() {
    @Autowired
    private val userService: UserDataService? = null
    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun authenticationProvider(): DaoAuthenticationProvider {
        val auth = DaoAuthenticationProvider()
        auth.setUserDetailsService(userService)
        auth.setPasswordEncoder(passwordEncoder())
        return auth
    }

    @Throws(Exception::class)
    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.authenticationProvider(authenticationProvider())
    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.authorizeRequests().antMatchers(
            "/registration**", "/js/**",
            "/css/**", "/img/**"
        ).permitAll().anyRequest()
            .authenticated().and().formLogin().loginPage("/login").permitAll().and().logout()
            .invalidateHttpSession(true).clearAuthentication(true)
            .logoutRequestMatcher(AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login?logout")
            .permitAll()
    }
}