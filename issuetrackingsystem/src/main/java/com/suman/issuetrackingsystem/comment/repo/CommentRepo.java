package com.suman.issuetrackingsystem.comment.repo;

import com.suman.issuetrackingsystem.comment.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepo extends JpaRepository<Comment,Long> {
    List<Comment> findByPostId(Long Id);
}
