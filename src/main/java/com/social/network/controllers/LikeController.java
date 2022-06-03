package com.social.network.controllers;

import com.social.network.entities.Comment;
import com.social.network.entities.Like;
import com.social.network.requests.CommentCreateRequest;
import com.social.network.requests.CommentUpdateRequest;
import com.social.network.requests.LikeCreateRequest;
import com.social.network.service.CommentService;
import com.social.network.service.LikeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/likes")
public class LikeController {
    private LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping
    public Like createLike(@RequestBody LikeCreateRequest likeCreateRequest) {
        return likeService.createLike(likeCreateRequest);
    }

    @GetMapping
    public List<Like> getALlLikes(
            @RequestParam Optional<Long> userId, @RequestParam Optional<Long> postId) {
        return likeService.getAllLikes(userId, postId);
    }

    @GetMapping("/{likeId}")
    public Like getLikeById(@PathVariable Long likeId) {
        return likeService.getLikeById(likeId);
    }


    @DeleteMapping("/{likeId}")
    public void deleteLike(@PathVariable Long likeId) {
        likeService.deleteLike(likeId);

    }
}
