package com.suman.issuetrackingsystem.post.controller;


import com.suman.issuetrackingsystem.post.dto.PostRequestDTO;
import com.suman.issuetrackingsystem.post.service.PostService;
import com.suman.issuetrackingsystem.user.model.UserPrincipal;
import com.suman.issuetrackingsystem.util.ApiResponseBuilder;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PostController {


    @Autowired
    private PostService postService;

    @PostMapping (value = "/posts")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")

    public ResponseEntity<?> createPost (@RequestBody PostRequestDTO dto, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        try{
            var response = postService.createPost(dto,userPrincipal.getId(),userPrincipal.getUsername());
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponseBuilder()
                    .status(HttpStatus.CREATED)
            .message("Post created successfully")
                    .data(response)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponseBuilder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .message("Error creating post: " + e.getMessage())
                    .build());
        }
    }

    @PostMapping("/posts/{postId}/submit-approval")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<?> submitForApproval(@PathVariable Long postId, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        try {
            var response = postService.submitForApproval(postId, userPrincipal.getId());
            return ResponseEntity.ok(new ApiResponseBuilder()
                    .status(HttpStatus.OK)
                    .message("Post submitted for approval")
                    .data(response)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponseBuilder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message(e.getMessage())
                    .build());
        }
    }






}
