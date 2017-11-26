package com.linecorp.springreact.controller

import com.linecorp.springreact.renderer.ReactRenderer
import com.linecorp.springreact.repository.PostRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import javax.servlet.http.HttpServletResponse

@Controller
class WebController {

    @Autowired
    lateinit var renderer: ReactRenderer

    @Autowired
    lateinit var postRepository: PostRepository

    @GetMapping(path = arrayOf("", "/"))
    fun getComments(response: HttpServletResponse) {
        response.setHeader("Content-Type", "text/html");
        response.writer.write(renderer.render(hashMapOf("posts" to
                hashMapOf("data" to postRepository.getPosts()))))
    }
}