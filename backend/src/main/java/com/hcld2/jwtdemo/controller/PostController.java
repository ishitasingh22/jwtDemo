package com.hcld2.jwtdemo.controller;

import com.hcld2.jwtdemo.entity.Post;
import com.hcld2.jwtdemo.repository.PostRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
@AllArgsConstructor
public class PostController {

    private final PostRepository postRepository;

    @GetMapping
    public List<Post> all() {
        return postRepository.findAllByOrderByCreatedAtDesc();
    }

    @PostMapping
    public Post create(@Valid @RequestBody Post req, Authentication auth) {

        Post post = new Post();
        post.setTitle(req.getTitle());
        post.setContent(req.getContent());
        post.setAuthor(auth.getName());

        return postRepository.save(post);
    }
}