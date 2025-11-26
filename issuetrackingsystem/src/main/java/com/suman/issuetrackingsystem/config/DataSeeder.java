package com.suman.issuetrackingsystem.config;

import com.suman.issuetrackingsystem.user.model.Role;
import com.suman.issuetrackingsystem.user.model.User;
import com.suman.issuetrackingsystem.user.repo.RoleRepo;
import com.suman.issuetrackingsystem.user.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        seedRoles();
        seedAdminUser();
    }

    private void seedRoles() {
        if (!roleRepo.existsByRoleName("ROLE_ADMIN")) {
            Role adminRole = new Role();
            adminRole.setRoleName("ROLE_ADMIN");
            roleRepo.save(adminRole);
            System.out.println("Seeded: ROLE_ADMIN");
        }

        if (!roleRepo.existsByRoleName("ROLE_USER")) {
            Role userRole = new Role();
            userRole.setRoleName("ROLE_USER");
            roleRepo.save(userRole);
            System.out.println("Seeded: ROLE_USER");
        }
    }

    private void seedAdminUser() {
        if (!userRepo.existsByUsername("admin")) {

            Role adminRole = roleRepo.findByRoleName("ROLE_ADMIN");

            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setEmail("admin@example.com");
            admin.setFullName("Super Admin");
            admin.setActive(true);
            admin.setRoles(Collections.singletonList(adminRole));

            userRepo.save(admin);

            System.out.println("Super Admin created successfully!");
            System.out.println("Username: admin");
            System.out.println("Password: admin123");
        }
    }
}
