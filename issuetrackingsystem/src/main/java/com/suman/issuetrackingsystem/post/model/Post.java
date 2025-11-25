package com.suman.issuetrackingsystem.post.model;


import com.suman.issuetrackingsystem.user.model.User;
import com.suman.issuetrackingsystem.user.model.UserPrincipal;
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

    @Column(nullable = false)
    private String title;
    private String description;

    @Enumerated (EnumType.STRING)
    @Column(nullable = false)
    private PostType postType;

    @Enumerated (EnumType.STRING)
    private PostStatus postStatus = PostStatus.DRAFT;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    private LocalDateTime approvedAt = LocalDateTime.now();

    @Column(columnDefinition = "TEXT")
    private String adminNotes;


}
