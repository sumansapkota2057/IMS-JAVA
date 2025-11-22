package com.suman.issuetrackingsystem.post.service;


import com.suman.issuetrackingsystem.post.dto.PostRequestDTO;
import com.suman.issuetrackingsystem.post.dto.PostResponseDTO;
import com.suman.issuetrackingsystem.post.model.Post;
import com.suman.issuetrackingsystem.post.model.PostStatus;
import com.suman.issuetrackingsystem.post.repo.PostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private PostRepo postRepo;

    public PostResponseDTO createPost(PostRequestDTO dto, Long userId) {
        Post post = new Post();
        post.setTitle(dto.getTitle());
        post.setDescription(dto.getDescription());
        post.setPostType(dto.getPostType());
        post.setCreatedBy(userId);
        post.setPostStatus(dto.isSubmitForApproval() ? PostStatus.APPROVED : PostStatus.DRAFT);

        Post savedPost = postRepo.save(post);
        return mapToDTO(savedPost);
    }

    public PostResponseDTO submitForApproval(Long postId, Long userId) {
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        if (!post.getCreatedBy().equals((userId))) {
            throw new RuntimeException("You can only submit your own posts");
        }
        post.setPostStatus(PostStatus.PENDING);
        Post savedPost = postRepo.save(post);
        return mapToDTO(savedPost);
    }


    public PostResponseDTO approvePost (Long postId, Long adminId){
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        post.setPostStatus(PostStatus.APPROVED);
        post.setApprovedBy(adminId);
        post.setApprovedAt(LocalDateTime.now());
        Post savedPost=  postRepo.save(post);
        return  mapToDTO(savedPost);
    }

    public  PostResponseDTO rejectPost (Long postId,String reason,Long adminId){
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        post.setPostStatus(PostStatus.REJECTED);
        post.setRejectionReason(reason);
        post.setApprovedBy(adminId);
        Post saved = postRepo.save(post);
        return mapToDTO(saved);
    }

    public PostResponseDTO assignUpdate(Long postId, String update, Long adminId) {
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        post.setAssignedTo(adminId);
        post.setAdminUpdate(update);
        post.setUpdatedAt(LocalDateTime.now());

        Post saved = postRepo.save(post);
        return mapToDTO(saved);
    }

    public PostResponseDTO resolvePost(Long postId) {
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        post.setPostStatus(PostStatus.RESOLVED);
        post.setUpdatedAt(LocalDateTime.now());

        Post saved = postRepo.save(post);
        return mapToDTO(saved);
    }
    public List<PostResponseDTO> getUserPosts(Long userId) {
        List<Post> posts = postRepo.findByCreatedBy(userId);
        return posts.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public List<PostResponseDTO> getApprovedPosts() {
        List<Post> posts = postRepo.findByStatus(PostStatus.APPROVED);
        return posts.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public List<PostResponseDTO> getAllPosts() {
        List<Post> posts = postRepo.findAll();
        return posts.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public List<PostResponseDTO> getPendingApprovalPosts() {
        List<Post> posts = postRepo.findByStatus(PostStatus.PENDING);
        return posts.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public PostResponseDTO getPostById(Long postId) {
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        return mapToDTO(post);
    }

    private PostResponseDTO mapToDTO(Post post) {
        PostResponseDTO dto = new PostResponseDTO();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setDescription(post.getDescription());
        dto.setPostType(post.getPostType());
        dto.setStatus(post.getPostStatus());
        dto.setCreatedAt(post.getCreatedAt());
        dto.setUpdatedAt(post.getUpdatedAt());
        dto.setApprovedAt(post.getApprovedAt());
        dto.setRejectionReason(post.getRejectionReason());
        dto.setAdminUpdate(post.getAdminUpdate());
        return dto;
    }


}

