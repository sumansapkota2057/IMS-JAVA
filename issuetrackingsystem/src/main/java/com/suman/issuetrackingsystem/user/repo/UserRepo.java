package com.suman.issuetrackingsystem.user.repo;

import com.suman.issuetrackingsystem.user.model.Role;
import com.suman.issuetrackingsystem.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepo extends JpaRepository<User,Long> {

    User findByUsername(String username);
    User findByUsernameAndPassword(String username, String password);
    boolean existsByUsername(String username);
    long countByActiveTrue();
    long countByActiveFalse();

}
