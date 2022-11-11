package com.example.hack_for_change_backend.service


import com.example.hack_for_change_backend.model.Post
import com.example.hack_for_change_backend.repository.PostRepo
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service


@Service
class PostService(val postRepo: PostRepo, val userService: UserService) {

    fun findPostById(id: Long): Post {
        return postRepo.findById(id).orElseThrow {
            NoSuchElementException("[POST] $id NOT FOUND")
        }
    }

    fun findAll(): List<Post>{
        return postRepo.findAll()
    }

    fun checkPostExists(post: Post): Boolean = findAll().contains(post)

    fun createPost(post: Post, userId: Long): Post {
        return try {
            val user = userService.findUserById(userId)
            post.user = user
            postRepo.save(post)
        } catch (e: NoSuchElementException) {
            throw NoSuchElementException(e.message)
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

    fun addRemoveLike(postId: Long, userId: Long): Post {
        return try {
            val post = findPostById(postId)
            val user = userService.findUserById(userId)
            val a = post.likes.getOrDefault(user, false)
            post.likes[user] = !a
            post.likeCount = post.likes.filterValues { it }.count()
            postRepo.save(post)
        } catch (e: NoSuchElementException) {
            throw NoSuchElementException(e.message)
        }
    }

}