package com.example.hack_for_change_backend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.support.beans
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.web.servlet.invoke
import org.springframework.security.core.userdetails.User
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.web.servlet.function.ServerResponse
import org.springframework.web.servlet.function.router
@SpringBootApplication
class HackForChangeBackendApplication

@EnableWebSecurity
class SecurityConfig : WebSecurityConfigurerAdapter() {

	override fun configure(http: HttpSecurity?) {
		http {
			httpBasic {}
			authorizeRequests {
				authorize("/greetings/**", hasAuthority("ROLE_ADMIN"))
				authorize("/**", permitAll)
			}
		}
	}
}

fun main(args: Array<String>) {
	runApplication<HackForChangeBackendApplication>(*args) {
		addInitializers(beans {
			bean {

				fun user(user: String, pw: String, vararg roles: String) =
					User.withDefaultPasswordEncoder().username(user).password(pw).roles(*roles).build()

				InMemoryUserDetailsManager(user("alex", "admin", "USER"), user("admin", "admin", "USER", "ADMIN"))
			}
			bean {
				router {
					GET("/greetings") { request ->
						request.principal().map { it.name }.map { ServerResponse.ok().body(mapOf("greeting" to "Hello, $it")) }.orElseGet { ServerResponse.badRequest().build() }
					}
				}
			}
		})
	}
}
