package com.suman.issuetrackingsystem.comment.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class CommentCreateDTO {

    private String content;
    private Long postId;

}
