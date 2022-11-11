package com.example.hack_for_change_backend.controller

import com.example.hack_for_change_backend.model.Post
import com.example.hack_for_change_backend.service.PostService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@CrossOrigin(origins = ["http://localhost:3000"])
@RequestMapping("/posts")
class PostController(private val postService: PostService)
{

    @GetMapping("/findPosts")
    fun getAllPosts(): ResponseEntity<List<Post>> = ResponseEntity.ok(postService.findAll())

    @GetMapping("/findPostById/{id}")
    fun findPostById(@PathVariable("id") postId: Long): ResponseEntity<Post> {
        return try {
            ResponseEntity.ok(postService.findPostById(postId))
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message)
        }
    }

    @PostMapping("/addPost/{user_id}")
    fun addPost(@RequestBody post: Post, @PathVariable("user_id") userId: Long): ResponseEntity<Post> {
        return try {
            ResponseEntity.ok(postService.createPost(post, userId))
        } catch (e: NoSuchElementException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message)
        }
    }

    @PutMapping("/editPost/{id}")
    fun postEdit(@PathVariable("id") postId: Long, @RequestBody post: Post): ResponseEntity<Post> {
        return try {
            postService.editPost(postId, post)
        }catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message)
        }
    }

    @DeleteMapping("/deletePost" + "/{id}")
    fun deletePost(@PathVariable("id") postId: Long): ResponseEntity<HttpStatus> {
        return try {
            postService.deletePost(postId)
            ResponseEntity.ok().body(HttpStatus.OK)
        } catch (e: NoSuchElementException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message)
        }
    }

    // Adds or removes like if one has already been given by user
    @PatchMapping("/likeDislikePost/{post_id}/{user_id}")
    fun likeDislikePost(@PathVariable("post_id") postId: Long,
                        @PathVariable("user_id") userId: Long): ResponseEntity<Post> {
        return try {
            ResponseEntity.ok(postService.addRemoveLike(postId, userId))
        } catch (e: NoSuchElementException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, e.message)
        }
    }
}