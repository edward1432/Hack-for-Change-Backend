package com.example.hack_for_change_backend.service


import com.example.hack_for_change_backend.model.Post
import com.example.hack_for_change_backend.model.Venue
import com.example.hack_for_change_backend.repository.PostRepo
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.lang.IllegalArgumentException


@Service
class PostService(val postRepo: PostRepo) {

    fun findPostById(id: Long): Post {
        return postRepo.findById(id).orElseThrow {
            NoSuchElementException("[POST] $id NOT FOUND")
        }
    }

    fun findAll(): List<Post>{
        return postRepo.findAll()
    }

    fun checkPostExists(post: Post): Boolean = findAll().contains(post)

    fun createPost(post: Post): ResponseEntity<Post> {
        if (!checkPostExists(post)) {
            postRepo.save(post)
            return ResponseEntity.ok(post)
        } else {
            throw IllegalArgumentException("[POST] $post ALREADY EXISTS")
        }
    }


    fun editPost(postId: Long, postDetails: Post): ResponseEntity<Post> {
        try {
            val post = findPostById(postId)
            post.likeCount = postDetails.likeCount
            post.content = postDetails.content
            return ResponseEntity.ok(post)
        } catch (e: NoSuchElementException) {
            throw NoSuchElementException(e.message)
        }
    }

    fun deletePost(postId: Long): ResponseEntity<HttpStatus> {
        try {
            postRepo.delete(findPostById(postId))
            return ResponseEntity.ok(HttpStatus.OK)
        } catch (e: NoSuchElementException) {
            throw NoSuchElementException(e.message)
        }
    }

}