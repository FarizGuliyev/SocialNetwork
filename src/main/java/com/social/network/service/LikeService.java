package com.social.network.service;

import com.social.network.entities.Comment;
import com.social.network.entities.Like;
import com.social.network.entities.Post;
import com.social.network.entities.User;
import com.social.network.repositories.CommentRepository;
import com.social.network.repositories.LikeRepository;
import com.social.network.requests.CommentCreateRequest;
import com.social.network.requests.CommentUpdateRequest;
import com.social.network.requests.LikeCreateRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LikeService {
    private LikeRepository likeRepository;
    private UserService userService;

    private PostService postService;

    public LikeService(LikeRepository likeRepository, UserService userService, PostService postService) {
        this.likeRepository = likeRepository;
        this.postService = postService;
        this.userService = userService;
    }

    public Like createLike(LikeCreateRequest likeCreateRequest) {
        User user = userService.getUserById(likeCreateRequest.getUserId());
        Post post = postService.getPostByID(likeCreateRequest.getPostId());
        if (user != null && post != null) {
            Like likeToSave = new Like();

            likeToSave.setPost(post);
            likeToSave.setUser(user);

            return likeRepository.save(likeToSave);
        } else return null;
    }

    public List<Like> getAllLikes(Optional<Long> userId, Optional<Long> postId) {
        if (postId.isPresent()&& userId.isPresent()) {
            return likeRepository.findByUserIdAndPostId(postId.get(), userId.get());
        } else if (userId.isPresent()) {
            return likeRepository.findByUserId(userId.get());
        } else if (postId.isPresent()) {
            return likeRepository.findByPostId(postId.get());
        } else return likeRepository.findAll();
    }

    public Like getLikeById(Long likeId) {
        return likeRepository.findById(likeId).orElse(null);
    }




    public void deleteLike(Long likeId) {
        likeRepository.deleteById(likeId);
    }
}
