package com.linecorp.springreact.domain

data class Post(val id: String,
                val title: String,
                val body: String,
                val createdAt: String,
                val updatedAt: String,
                var comments: List<Comment>) {
}