package com.social.network.service;

import com.social.network.entities.Post;
import com.social.network.entities.User;
import com.social.network.repositories.PostRepository;
import com.social.network.requests.PostCreateRequest;
import com.social.network.requests.PostUpdateRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    final
    PostRepository postRepository;

    final UserService userService;


    public PostService(PostRepository postRepository, UserService userService) {
        this.postRepository = postRepository;
        this.userService = userService;
    }

    public Post createPost(PostCreateRequest post) {
        User user = userService.getUserById(post.getUserId());
        if (user == null)
            return null;
        Post post1 = new Post();

        post1.setText(post.getText());
        post1.setTitle(post.getTitle());
        post1.setUser(user);
        return postRepository.save(post1);
    }

    public List<Post> getAllPosts(Optional<Long> userId) {
        if (userId.isPresent()) {
            return postRepository.findByUserId(userId.get());
        }
        return postRepository.findAll();
    }

    public Post getPostByID(Long id) {
        return postRepository.findById(id).orElse(null);
    }

    public Post updatePost(Long postId, PostUpdateRequest postUpdateRequest) {
        Optional<Post> post = postRepository.findById(postId);
        if (post.isPresent()) {
            Post post1 = post.get();
            post1.setText(postUpdateRequest.getText());
            post1.setTitle(postUpdateRequest.getTitle());
            postRepository.save(post1);
            return post1;
        }
        return null;
    }

    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }
}
