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
            .withUser("alex1")
            .password(encoder().encode("admin"))
            .roles("ADMIN")
            .and()
            .withUser("edward1")
            .password(encoder().encode("admin"))
            .roles("ADMIN")
    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.httpBasic()
            .and()
            .authorizeRequests()
            .antMatchers(HttpMethod.GET, "/enjoyer").hasRole("ADMIN")
            .antMatchers(HttpMethod.POST, "/enjoyer/**").hasRole("ADMIN")
            .antMatchers(HttpMethod.PUT, "/enjoyer/**").hasRole("ADMIN")
            .antMatchers(HttpMethod.DELETE, "/enjoyer/**").hasRole("ADMIN")
            .antMatchers(HttpMethod.GET, "/enjoyer/**").hasAnyRole("ADMIN", )
            .and()
            .csrf().disable()
            .formLogin().disable()
    }

}