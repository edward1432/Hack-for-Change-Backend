package com.example.hack_for_change_backend.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import javax.persistence.Column
import javax.persistence.ElementCollection
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "posts")
data class Post(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val uniqueId: Long,
    @Column
    var content: String,
    @Column
    var likeCount: Int,
    @JsonIgnoreProperties("posts")
    @JsonIgnore
    @ManyToOne
    var user: User?
        ) {
    @JsonIgnore
    @ElementCollection
    val likes: MutableMap<User, Boolean> = mutableMapOf()
}