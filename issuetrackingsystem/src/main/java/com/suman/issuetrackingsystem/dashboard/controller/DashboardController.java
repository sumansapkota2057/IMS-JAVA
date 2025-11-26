package com.suman.issuetrackingsystem.dashboard.controller;


import com.suman.issuetrackingsystem.dashboard.dto.DashboardDTO;
import com.suman.issuetrackingsystem.dashboard.service.DashboardService;
import com.suman.issuetrackingsystem.util.ApiResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/api/dashboard")
@CrossOrigin(origins = "*")

public class DashboardController {
    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/stats")
    public ResponseEntity<?> getStats() {
        try {
            DashboardDTO stats = dashboardService.getStats();
            return ResponseEntity.ok(new ApiResponseBuilder()
                    .status(HttpStatus.OK)
                    .message("Dashboard stats retrieved successfully")
                    .data(stats)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponseBuilder()
                            .status(HttpStatus.BAD_REQUEST)
                            .message(e.getMessage())
                            .build());
        }
    }

}
