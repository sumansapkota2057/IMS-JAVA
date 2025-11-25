package com.suman.issuetrackingsystem.post.repo;

import com.suman.issuetrackingsystem.post.model.Post;
import com.suman.issuetrackingsystem.post.model.PostStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepo extends JpaRepository<Post,Long> {

    List<Post> findByCreatedBy(Long userId);
    List<Post> findByPostStatus(PostStatus postStatus);
    List<Post> findByCreatedByAndPostStatus(Long userId, PostStatus postStatus);
    long countByStatus(PostStatus status);
}
