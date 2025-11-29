package com.suman.issuetrackingsystem.post.service;


import com.suman.issuetrackingsystem.exception.ResourceNotFoundException;
import com.suman.issuetrackingsystem.post.dto.PostRequestDTO;
import com.suman.issuetrackingsystem.post.dto.PostResponseDTO;
import com.suman.issuetrackingsystem.post.dto.PostUpdateDTO;
import com.suman.issuetrackingsystem.post.model.Post;
import com.suman.issuetrackingsystem.post.model.PostStatus;
import com.suman.issuetrackingsystem.post.repo.PostRepo;
import com.suman.issuetrackingsystem.user.model.User;
import com.suman.issuetrackingsystem.user.model.UserPrincipal;
import com.suman.issuetrackingsystem.user.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PostMapper postMapper;


    public PostResponseDTO createPost(PostRequestDTO dto, String userName) {
        User user = userRepo.findByUsername(userName);
        if (user == null) {
            throw new ResourceNotFoundException("User not found with username: " + userName);
        }


        Post post = new Post();
        post.setTitle(dto.getTitle());
        post.setDescription(dto.getDescription());
        post.setPostType(dto.getPostType());
        post.setPostStatus(PostStatus.DRAFT);
        post.setUser(user);

        Post saved = postRepo.save(post);
        return  postMapper.mapToDto(saved);
    }

    public PostResponseDTO submitForApproval(Long postId, String username) {
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));

        if (!post.getUser().getUsername().equals(username)) {
            throw new RuntimeException("Unauthorized");
        }

        if (post.getPostStatus() != PostStatus.DRAFT) {
            throw new RuntimeException("Only DRAFT posts can be submitted");
        }
        post.setPostStatus(PostStatus.PENDING);
        post.setUpdatedAt(LocalDateTime.now());
        Post savedPost = postRepo.save(post);
        return postMapper.mapToDto(savedPost);

    }


    public PostResponseDTO  adminApprove(Long postId, String adminNotes) {
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));

        if (post.getPostStatus() != PostStatus.PENDING) {
            throw new RuntimeException("Only PENDING_APPROVAL posts can be approved");
        }

        post.setPostStatus(PostStatus.APPROVED);
        post.setAdminNotes(adminNotes);
        post.setUpdatedAt(LocalDateTime.now());
        post.setApprovedAt(LocalDateTime.now());
        Post saved = postRepo.save(post);
        return postMapper.mapToDto(saved);

    }

    public  PostResponseDTO adminReject (Long postId, String adminNotes){
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));

        if (post.getPostStatus() != PostStatus.PENDING) {
            throw new RuntimeException("Only PENDING_APPROVAL posts can be rejected");
        }

        post.setPostStatus(PostStatus.REJECTED);
        post.setAdminNotes(adminNotes);
        post.setUpdatedAt(LocalDateTime.now());
        Post saved = postRepo.save(post);
        return postMapper.mapToDto(saved);
    }

    public PostResponseDTO resolvePost(Long postId, String adminNotes) {
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));

        if (post.getPostStatus() != PostStatus.APPROVED) {
            throw new RuntimeException("Only APPROVED posts can be resolved");
        }

        post.setPostStatus(PostStatus.RESOLVED);
        post.setAdminNotes(adminNotes);
        post.setUpdatedAt(LocalDateTime.now());

        Post saved = postRepo.save(post);
        return postMapper.mapToDto(saved);
    }

    public PostResponseDTO updatePost(Long postId, PostUpdateDTO dto, String username) {
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));

        if (!post.getUser().getUsername().equals(username)) {
            throw new RuntimeException("Unauthorized");
        }

        if (dto.getTitle() != null) post.setTitle(dto.getTitle());
        if (dto.getDescription() != null) post.setDescription(dto.getDescription());
        post.setUpdatedAt(LocalDateTime.now());

        Post saved = postRepo.save(post);
        return postMapper.mapToDto(saved);


    }


    public List<PostResponseDTO> getMyPosts(String username) {
        User user = userRepo.findByUsername(username);
        if (user == null) {
            throw new ResourceNotFoundException("User not found with username: " + username);
        }

        List<Post> posts = postRepo.findByUser_Id(user.getId());

        return postMapper.toDtoList(posts);
    }

    public List<PostResponseDTO> getApprovedPosts() {
        List<Post> posts = postRepo.findByPostStatus(PostStatus.APPROVED);
        return postMapper.toDtoList(posts);
    }



    public List<PostResponseDTO> getAllPosts() {
        return postMapper.toDtoList(postRepo.findByPostStatusNot(PostStatus.DRAFT));
    }

    public List<PostResponseDTO> getPendingPosts() {
        List<Post> posts = postRepo.findByPostStatus(PostStatus.PENDING);
        return postMapper.toDtoList(posts);
    }


    public PostResponseDTO getPostDtoById(Long id) {
        Post post = postRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));

        return postMapper.mapToDto(post);
    }
    public List<PostResponseDTO> getMyPostsByStatus(String username, PostStatus postStatus) {
        User user = userRepo.findByUsername(username);
        if (user == null) {
            throw new ResourceNotFoundException("User not found with username: " + username);
        }
        List<Post> posts = postRepo.findByUser_IdAndPostStatus(user.getId(), postStatus);
        return postMapper.toDtoList(posts);
    }

    public long getTotalPosts(){
        return postRepo.countByPostStatusNot(PostStatus.DRAFT);
    }

    public long countPostsByStatus(PostStatus status) {
        return postRepo.countByPostStatus(status);
    }
    public List<PostResponseDTO> getPostsByStatus(PostStatus status) {
        List<Post> posts = postRepo.findByPostStatus(status);
        return postMapper.toDtoList(posts);
    }


}

