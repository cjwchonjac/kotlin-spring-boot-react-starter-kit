package com.linecorp.springreact.repository

import com.linecorp.springreact.domain.Comment
import com.linecorp.springreact.domain.Post
import org.springframework.stereotype.Repository
import java.util.*
import javax.annotation.PostConstruct

@Repository
class PostRepository {
    private val allPosts: MutableList<Post> = ArrayList()

    @PostConstruct
    fun init() {
        val comments1: ArrayList<Comment> = ArrayList()
        val comments2: ArrayList<Comment> = ArrayList()
        val comments3: ArrayList<Comment> = ArrayList()
        val comments4: ArrayList<Comment> = ArrayList()

        allPosts.add(Post("1", "title1", "body1", "2017-01-02", "2017-01-03", comments1))
        allPosts.add(Post("2", "title2", "body2", "2017-01-04", "2017-01-05", comments2))
        allPosts.add(Post("3", "title3", "body3", "2017-01-06", "2017-01-07", comments3))
        allPosts.add(Post("4", "title4", "body4", "2017-01-08", "2017-01-09", comments4))

        for (idx in 1..20) {
            comments1.add(Comment("1-$idx", "1 message $idx", "2017-01-010", "2017-01-11"))
            comments2.add(Comment("2-$idx", "2 message $idx", "2017-01-010", "2017-01-11"))
            comments3.add(Comment("3-$idx", "3 message $idx", "2017-01-010", "2017-01-11"))
            comments4.add(Comment("4-$idx", "4 message $idx", "2017-01-010", "2017-01-11"))
        }
    }

    fun getPosts(): List<Post> {
        return allPosts.map { it.copy(comments = it.comments.take(5))  }
    }

    fun getComments(postId: String, offset: Int, limit: Int): List<Comment> {
        val post = allPosts.filter { it.id == postId }.firstOrNull()
        return post?.comments?.drop(offset)?.take(limit) ?: throw RuntimeException()
    }
}