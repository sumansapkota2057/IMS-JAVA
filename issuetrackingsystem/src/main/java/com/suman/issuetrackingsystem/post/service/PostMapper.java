package com.suman.issuetrackingsystem.post.service;

import com.suman.issuetrackingsystem.post.dto.PostResponseDTO;
import com.suman.issuetrackingsystem.post.model.Post;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PostMapper {

    public PostResponseDTO mapToDto(Post post) {
        PostResponseDTO dto = new PostResponseDTO();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setDescription(post.getDescription());
        dto.setPostType(post.getPostType());
        dto.setStatus(post.getPostStatus());
        dto.setAdminNotes(post.getAdminNotes());
        dto.setCreatedAt(post.getCreatedAt());
        dto.setUpdatedAt(post.getUpdatedAt());
        dto.setUsername(post.getUser().getUsername());
        return dto;
    }

    public List<PostResponseDTO> toDtoList(List<Post> posts) {
        return posts.stream()
                .map(this::mapToDto)
                .toList();
    }
}
