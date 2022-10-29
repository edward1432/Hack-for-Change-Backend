package com.example.hack_for_change_backend.model

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.userdetails.UserDetails

//class UserAuthentication(user: User) : User(user), UserDetails {
//
//    override fun getAuthorities(): Collection<GrantedAuthority> {
//        return AuthorityUtils.createAuthorityList("USER")
//    }
//
//    override fun getUsername(): String {
//        return email
//    }
//
//    override fun getPassword(): String {
//        return password
//    }
//
//    override fun isAccountNonExpired(): Boolean {
//        return true
//    }
//
//    override fun isAccountNonLocked(): Boolean {
//        return true
//    }
//
//    override fun isCredentialsNonExpired(): Boolean {
//        return true
//    }
//
//    override fun isEnabled(): Boolean {
//        return true
//    }
//}