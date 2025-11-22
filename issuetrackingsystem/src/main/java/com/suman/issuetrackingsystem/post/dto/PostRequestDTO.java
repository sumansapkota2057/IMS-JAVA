package com.suman.issuetrackingsystem.post.dto;

import com.suman.issuetrackingsystem.post.model.PostType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostRequestDTO {
    private String title;
    private String description;
    private PostType postType;
    private boolean submitForApproval;
}
