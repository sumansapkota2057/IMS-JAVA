package com.suman.issuetrackingsystem.post.repo;

import com.suman.issuetrackingsystem.post.model.Post;
import com.suman.issuetrackingsystem.post.model.PostStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepo extends JpaRepository<Post,Long> {

    List<Post> findByUser_Id(Long userId);

    List<Post> findByPostStatus(PostStatus postStatus);

    List<Post> findByPostStatusNot(PostStatus postStatus);

    List<Post> findByUser_IdAndPostStatus(Long userId, PostStatus postStatus);

    long countByPostStatus(PostStatus status);
    long countByPostStatusNot(PostStatus status);
    List<Post> findByPostStatusOrderByCreatedAtDesc(PostStatus postStatus);
}
