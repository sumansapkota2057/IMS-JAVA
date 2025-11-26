package com.suman.issuetrackingsystem.post.controller;


import com.suman.issuetrackingsystem.post.dto.PostRequestDTO;
import com.suman.issuetrackingsystem.post.dto.PostResponseDTO;
import com.suman.issuetrackingsystem.post.dto.PostUpdateDTO;
import com.suman.issuetrackingsystem.post.model.Post;
import com.suman.issuetrackingsystem.post.service.PostService;
import com.suman.issuetrackingsystem.util.ApiResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")


public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping
    public ResponseEntity<?> createPost(@RequestBody PostRequestDTO dto, Authentication auth) {
        PostResponseDTO post = postService.createPost(dto, auth.getName());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponseBuilder()
                        .status(HttpStatus.CREATED)
                        .message("Post created successfully")
                        .data(post)
                        .build());
    }

    @PutMapping("/{id}/submit")
    public ResponseEntity<?> submitForApproval(@PathVariable Long id, Authentication auth) {
        PostResponseDTO post = postService.submitForApproval(id, auth.getName());
        return ResponseEntity.ok(new ApiResponseBuilder()
                .status(HttpStatus.OK)
                .message("Post submitted for approval")
                .data(post)
                .build());
    }

    @PutMapping("/{id}/approve")
    public ResponseEntity<?> approvePost(@PathVariable Long id, @RequestParam(required = false) String notes) {
        PostResponseDTO post = postService.adminApprove(id, notes);
        return ResponseEntity.ok(new ApiResponseBuilder()
                .status(HttpStatus.OK)
                .message("Post approved successfully")
                .data(post)
                .build());
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<?> rejectPost(@PathVariable Long id, @RequestParam(required = false) String notes) {
        PostResponseDTO post = postService.adminReject(id, notes);
        return ResponseEntity.ok(new ApiResponseBuilder()
                .status(HttpStatus.OK)
                .message("Post rejected")
                .data(post)
                .build());
    }

    @PutMapping("/{id}/resolve")
    public ResponseEntity<?> resolvePost(@PathVariable Long id, @RequestParam(required = false) String notes) {
        PostResponseDTO post = postService.resolvePost(id, notes);
        return ResponseEntity.ok(new ApiResponseBuilder()
                .status(HttpStatus.OK)
                .message("Post resolved")
                .data(post)
                .build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePost(@PathVariable Long id, @RequestBody PostUpdateDTO dto, Authentication auth) {
        PostResponseDTO post = postService.updatePost(id, dto, auth.getName());
        return ResponseEntity.ok(new ApiResponseBuilder()
                .status(HttpStatus.OK)
                .message("Post updated successfully")
                .data(post)
                .build());
    }

    @GetMapping("/my-posts")
    public ResponseEntity<?> getMyPosts(Authentication auth) {
        List<PostResponseDTO> posts = postService.getMyPosts(auth.getName());
        return ResponseEntity.ok(new ApiResponseBuilder()
                .status(HttpStatus.OK)
                .message("Posts retrieved successfully")
                .data(posts)
                .build());
    }

    @GetMapping("/approved")
    public ResponseEntity<?> getApprovedPosts() {
        List<PostResponseDTO> posts = postService.getApprovedPosts();
        return ResponseEntity.ok(new ApiResponseBuilder()
                .status(HttpStatus.OK)
                .message("Approved posts retrieved successfully")
                .data(posts)
                .build());
    }

    @GetMapping
    public ResponseEntity<?> getAllPosts() {
        List<PostResponseDTO> posts = postService.getAllPosts();
        return ResponseEntity.ok(new ApiResponseBuilder()
                .status(HttpStatus.OK)
                .message("All posts retrieved successfully")
                .data(posts)
                .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPostById(@PathVariable Long id) {
        PostResponseDTO post = postService.getPostDtoById(id);
        return ResponseEntity.ok(new ApiResponseBuilder()
                .status(HttpStatus.OK)
                .message("Post retrieved successfully")
                .data(post)
                .build());
    }
}
