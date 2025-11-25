package com.suman.issuetrackingsystem.user.model;


import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@Entity (name="users")
@AllArgsConstructor
@NoArgsConstructor

public class User {
      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      private Long id;
      @Column (unique = true)
      private  String username;

      @Column (nullable = false,unique = true)
      private String email;

      private String fullName;


      private boolean active = true;


      @Column(nullable = false)
      private  String password;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
      )
    private List<Role> roles;

}
