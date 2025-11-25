package com.suman.issuetrackingsystem.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationDTO {

    private String username;
    private String password;
    private String email;
    private String fullName;
    private List<String> roles;
}
