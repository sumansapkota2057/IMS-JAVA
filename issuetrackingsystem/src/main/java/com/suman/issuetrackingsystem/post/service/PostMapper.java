package com.suman.issuetrackingsystem.post.service;

import com.suman.issuetrackingsystem.post.dto.PostResponseDTO;
import com.suman.issuetrackingsystem.post.model.Post;


import com.suman.issuetrackingsystem.user.dto.UserDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PostMapper {


    public PostResponseDTO mapToDto(Post post) {

        UserDTO userDTO = null;
        if (post.getUser() != null) {
            userDTO = new UserDTO(post.getUser().getId(), post.getUser().getUsername());
        }



        PostResponseDTO dto = new PostResponseDTO();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setDescription(post.getDescription());
        dto.setPostType(post.getPostType());
        dto.setStatus(post.getPostStatus());
        dto.setAdminNotes(post.getAdminNotes());
        dto.setCreatedAt(post.getCreatedAt());
        dto.setUpdatedAt(post.getUpdatedAt());
        dto.setUser(userDTO);

        return dto;
    }

    public List<PostResponseDTO> toDtoList(List<Post> posts) {
        return posts.stream()
                .map(this::mapToDto)
                .toList();
    }
}

