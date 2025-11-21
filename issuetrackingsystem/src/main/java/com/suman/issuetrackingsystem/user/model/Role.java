package com.suman.issuetrackingsystem.user.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity (name = "role")
@NoArgsConstructor
@AllArgsConstructor

public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(unique = true)
    private String roleName;

    public Role(String roleName) {
        this.roleName = roleName;
    }



}
