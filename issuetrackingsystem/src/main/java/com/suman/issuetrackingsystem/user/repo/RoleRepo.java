package com.suman.issuetrackingsystem.user.repo;

import com.suman.issuetrackingsystem.user.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleRepo extends JpaRepository<Role,Long> {
    Role findByRoleName(String roleName);

}
