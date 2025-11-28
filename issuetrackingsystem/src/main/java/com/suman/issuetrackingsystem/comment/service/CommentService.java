package com.suman.issuetrackingsystem.comment.service;

import com.suman.issuetrackingsystem.comment.dto.CommentCreateDTO;
import com.suman.issuetrackingsystem.comment.dto.CommentResponseDTO;
import com.suman.issuetrackingsystem.comment.model.Comment;
import com.suman.issuetrackingsystem.comment.repo.CommentRepo;
import com.suman.issuetrackingsystem.exception.ResourceNotFoundException;
import com.suman.issuetrackingsystem.post.model.Post;
import com.suman.issuetrackingsystem.post.repo.PostRepo;
import com.suman.issuetrackingsystem.user.model.User;
import com.suman.issuetrackingsystem.user.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private UserRepo userRepo;

    public CommentResponseDTO addComment(CommentCreateDTO dto, String username) {
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

        Comment saved = commentRepo.save(comment);

        return new CommentResponseDTO(
                saved.getId(),
                saved.getContent(),
                saved.getUser().getUsername(),
                saved.getPost().getId(),
                saved.getCreatedAt().toString()
        );
    }

    public List<CommentResponseDTO> getCommentsByPostId(Long postId) {
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));

        List<Comment> comments = commentRepo.findByPostId(post.getId());

        return comments.stream()
                .map(c -> new CommentResponseDTO(
                        c.getId(),
                        c.getContent(),
                        c.getUser().getUsername(),
                        c.getPost().getId(),
                        c.getCreatedAt().toString()
                ))
                .collect(Collectors.toList());
    }
    public void deleteComment(Long commentId, String username) {
        Comment comment = commentRepo.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found"));
        if (!comment.getUser().getUsername().equals(username)) {
            throw new AccessDeniedException("You are not authorized to delete this comment");
        }

        commentRepo.delete(comment);
    }

}
