package com.suman.issuetrackingsystem.user.service;


import com.suman.issuetrackingsystem.user.model.Role;
import com.suman.issuetrackingsystem.user.model.User;
import com.suman.issuetrackingsystem.user.repo.RoleRepo;
import com.suman.issuetrackingsystem.user.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    public User registerUser(User user) {
        List<Role> roles = new ArrayList<>();
        for(Role role : user.getRoles()) {
            Role existingRole = roleRepo.findByRoleName(role.getRoleName());

            if(existingRole == null) {
                throw new RuntimeException("Role not found: " + role.getRoleName());
            }
            roles.add(existingRole);
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(roles);
        userRepo.save(user);

        return user;
    }

    public List<User> getAllUser() {
        return userRepo.findAll();
    }


    public void validateUser(User user) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
    }
}

