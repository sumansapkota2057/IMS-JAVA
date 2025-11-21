package com.suman.issuetrackingsystem.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.suman.issuetrackingsystem.user.model.User;
import com.suman.issuetrackingsystem.user.service.UserService;
import com.suman.issuetrackingsystem.util.ApiResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public ResponseEntity<Map<String, Object>> getAllUsers() {
        List<User> users = userService.getAllUser();
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponseBuilder()
                        .status(HttpStatus.OK)
                        .message("Users fetched successfully")
                        .data(users)
                        .build()
        );
    }



}