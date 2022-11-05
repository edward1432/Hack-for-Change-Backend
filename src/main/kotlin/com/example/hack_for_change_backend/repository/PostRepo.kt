package com.example.hack_for_change_backend.repository

import com.example.hack_for_change_backend.model.Post
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface PostRepo : JpaRepository<Post, Long> {

    @Query(value = "SELECT * FROM posts WHERE id = ?", nativeQuery = true)
    fun findPostById(id: Long): Post

    @Query(value = "SELECT * FROM posts WHERE lower(post) LIKE lower(?1)", nativeQuery = true)
    fun findPostByKeyWord(@Param("posts") posts: String): List<Post>



}