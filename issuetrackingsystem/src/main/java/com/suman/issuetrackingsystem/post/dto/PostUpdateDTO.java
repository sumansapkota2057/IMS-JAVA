package com.suman.issuetrackingsystem.post.dto;

import com.suman.issuetrackingsystem.post.model.PostStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostUpdateDTO {
    private String title;
    private String description;
    private PostStatus status;
    private String adminNotes;
}
