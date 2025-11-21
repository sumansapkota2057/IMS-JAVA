package com.suman.issuetrackingsystem.user.service;

import com.suman.issuetrackingsystem.user.model.Role;
import com.suman.issuetrackingsystem.user.repo.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    private RoleRepo roleRepo;

    public void saveRole(Role role) {
        roleRepo.save(role);
    }

    public List<Role> getAllRoles() {
        return roleRepo.findAll();
    }

    public Role getRoleByName(String roleName) {
        return roleRepo.findByRoleName(roleName);
    }

}

