package com.suman.issuetrackingsystem.post.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity (name= "posts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;

    @Enumerated (EnumType.STRING)
    private PostType postType;

    @Enumerated (EnumType.STRING)
    private PostStatus postStatus = PostStatus.DRAFT;

    private  Long createdBy;
    private String createdByName;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();

    private Long approvedBy;
    private LocalDateTime approvedAt;

    private String rejectionReason;
    private Long assignedTo;
    private String adminUpdate;

}
