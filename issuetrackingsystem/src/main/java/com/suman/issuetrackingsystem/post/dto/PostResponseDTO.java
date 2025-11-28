package com.suman.issuetrackingsystem.post.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.suman.issuetrackingsystem.post.model.PostStatus;
import com.suman.issuetrackingsystem.post.model.PostType;
import com.suman.issuetrackingsystem.user.dto.UserDTO;
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

    @JsonProperty("type")
    private PostType postType;

    private PostStatus status;
    private LocalDateTime createdAt;

    private UserDTO user;

    private LocalDateTime updatedAt;
    private LocalDateTime approvedAt;
    private String adminNotes;


}
