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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PostController {


    @Autowired
    private PostService postService;

    @PostMapping (value = "/posts")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")

    public ResponseEntity<?> createPost (@RequestBody PostRequestDTO dto, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        try{
            var response = postService.createPost(dto,userPrincipal.getId());
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





}
