package com.suman.issuetrackingsystem.post.dto;


import com.suman.issuetrackingsystem.post.model.PostStatus;
import com.suman.issuetrackingsystem.post.model.PostType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class PostResponseDTO {
    private Long id;
    private String title;
    private String description;
    private PostType postType;
    private PostStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime approvedAt;
    private String rejectionReason;
    private String adminUpdate;

}
