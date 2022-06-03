package com.social.network.service;

import com.social.network.entities.Comment;
import com.social.network.entities.Post;
import com.social.network.entities.User;
import com.social.network.repositories.CommentRepository;
import com.social.network.requests.CommentCreateRequest;
import com.social.network.requests.CommentUpdateRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    private CommentRepository commentRepository;

    private UserService userService;

    private PostService postService;

    public CommentService(CommentRepository commentRepository, UserService userService, PostService postService) {
        this.commentRepository = commentRepository;
        this.postService = postService;
        this.userService = userService;
    }

    public Comment createComment(CommentCreateRequest commentCreateRequest) {
        User user = userService.getUserById(commentCreateRequest.getUserId());
        Post post = postService.getPostByID(commentCreateRequest.getPostId());
        if (user != null && post != null) {
            Comment commentToSave = new Comment();

            commentToSave.setPost(post);
            commentToSave.setUser(user);
            commentToSave.setText(commentCreateRequest.getText());
            return commentRepository.save(commentToSave);
        } else return null;
    }

    public List<Comment> getAllComments(Optional<Long> userId, Optional<Long> postId) {
        if (userId.isPresent() && postId.isPresent()) {
            return commentRepository.findByUserIdAndPostId(postId.get(), userId.get());
        } else if (userId.isPresent()) {
            return commentRepository.findByUserId(userId.get());
        } else if (postId.isPresent()) {
            return commentRepository.findByPostId(postId.get());
        } else return commentRepository.findAll();
    }

    public Comment getCommentById(Long commentId) {
        return commentRepository.findById(commentId).orElse(null);
    }


    public Comment updateComment(Long commentId, CommentUpdateRequest comment) {
        Optional<Comment> comment1 = commentRepository.findById(commentId);
        if (comment1.isPresent()) {
            Comment commentToUpdate = comment1.get();

            commentToUpdate.setText(comment.getText());

            return commentRepository.save(commentToUpdate);

        } else return null;
    }

    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
