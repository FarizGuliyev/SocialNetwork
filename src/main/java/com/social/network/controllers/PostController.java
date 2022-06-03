package com.social.network.controllers;

import com.social.network.entities.Post;
import com.social.network.requests.PostCreateRequest;
import com.social.network.requests.PostUpdateRequest;
import com.social.network.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    PostService postService;

    @PostMapping
    public Post createPost(@RequestBody PostCreateRequest post){
        return postService.createPost(post);
    }

    @GetMapping
    public List<Post> getAllPosts(@RequestParam Optional<Long> userId) {
        return postService.getAllPosts(userId);
    }

    @GetMapping("/{postId}")
    public Post getPostByID(@PathVariable Long postId){
        return postService.getPostByID(postId);
    }

    @PutMapping("/{postId}")
    public Post updatePost(@PathVariable Long postId,@RequestBody PostUpdateRequest post){
        return postService.updatePost(postId,post);
    }

    @DeleteMapping("/{postId}")
    public void deletePost(@PathVariable Long postId){
        postService.deletePost(postId);
    }

}
