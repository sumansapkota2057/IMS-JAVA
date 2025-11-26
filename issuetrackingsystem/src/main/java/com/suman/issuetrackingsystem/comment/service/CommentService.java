package com.suman.issuetrackingsystem.comment.service;

import com.suman.issuetrackingsystem.comment.dto.CommentCreateDTO;
import com.suman.issuetrackingsystem.comment.model.Comment;
import com.suman.issuetrackingsystem.comment.repo.CommentRepo;
import com.suman.issuetrackingsystem.exception.ResourceNotFoundException;
import com.suman.issuetrackingsystem.post.model.Post;
import com.suman.issuetrackingsystem.post.repo.PostRepo;
import com.suman.issuetrackingsystem.user.model.User;
import com.suman.issuetrackingsystem.user.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private UserRepo userRepo;

    public Comment addComment(CommentCreateDTO dto, String username) {
        User user = userRepo.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found with username: " + username);
        }

        Post post = postRepo.findById(dto.getPostId())
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));

        Comment comment = new Comment();
        comment.setContent(dto.getContent());
        comment.setPost(post);
        comment.setUser(user);

        return commentRepo.save(comment);
    }

    public List<Comment> getCommentsByPostId(Long postId) {
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));

        return commentRepo.findByPostId(post.getId());
    }
}
