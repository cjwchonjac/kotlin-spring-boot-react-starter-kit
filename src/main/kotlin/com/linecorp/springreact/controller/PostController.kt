package com.linecorp.springreact.controller

import com.linecorp.springreact.domain.Comment
import com.linecorp.springreact.domain.Post
import com.linecorp.springreact.repository.PostRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/post")
class PostController {

    @Autowired
    lateinit var postRepository: PostRepository

    @GetMapping("/list")
    fun getPost(): List<Post> {
        return postRepository.getPosts()
    }

    @GetMapping("/comments")
    fun getComments(@RequestParam("postId") postId: String,
                    @RequestParam(value = "offset", required = false, defaultValue = "0") offset: Int,
                    @RequestParam(value = "limit", required = false, defaultValue = "20") limit: Int):
            List<Comment> {

        return postRepository.getComments(postId, offset, limit)
    }
}