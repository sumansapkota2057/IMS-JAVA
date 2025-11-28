package com.suman.issuetrackingsystem.post.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.suman.issuetrackingsystem.post.model.PostType;
import com.suman.issuetrackingsystem.user.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostRequestDTO {
    private String title;
    private String description;

    @JsonProperty("type")
    private PostType postType;





}
