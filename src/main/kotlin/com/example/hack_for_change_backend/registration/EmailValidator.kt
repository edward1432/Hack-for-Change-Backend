package com.example.hack_for_change_backend.registration

import org.springframework.stereotype.Service
import java.util.function.Predicate


@Service
class EmailValidator : Predicate<String?> {
    override fun test(s: String?): Boolean {
//        TODO: Regex to validate email
        return true
    }
}