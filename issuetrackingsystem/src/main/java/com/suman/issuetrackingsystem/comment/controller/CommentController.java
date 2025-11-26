package com.suman.issuetrackingsystem.comment.controller;

import com.suman.issuetrackingsystem.comment.dto.CommentCreateDTO;
import com.suman.issuetrackingsystem.comment.model.Comment;
import com.suman.issuetrackingsystem.comment.service.CommentService;
import com.suman.issuetrackingsystem.util.ApiResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/api")
@CrossOrigin(origins = "*")

public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping ("/comments")
    public ResponseEntity<?> addComment(@RequestBody CommentCreateDTO dto, Authentication auth) {
        try {
            Comment comment = commentService.addComment(dto, auth.getName());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponseBuilder()
                            .status(HttpStatus.CREATED)
                            .message("Comment added successfully")
                            .data(comment)
                            .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponseBuilder()
                            .status(HttpStatus.BAD_REQUEST)
                            .message(e.getMessage())
                            .build());
        }
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<?> getCommentsByPost(@PathVariable Long postId) {
        try {
            List<Comment> comments = commentService.getCommentsByPostId(postId);
            return ResponseEntity.ok(new ApiResponseBuilder()
                    .status(HttpStatus.OK)
                    .message("Comments retrieved successfully")
                    .data(comments)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponseBuilder()
                            .status(HttpStatus.BAD_REQUEST)
                            .message(e.getMessage())
                            .build());
        }
    }

}
