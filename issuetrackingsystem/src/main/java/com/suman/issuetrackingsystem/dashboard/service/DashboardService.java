package com.suman.issuetrackingsystem.dashboard.service;


import com.suman.issuetrackingsystem.dashboard.dto.DashboardDTO;
import com.suman.issuetrackingsystem.post.model.PostStatus;
import com.suman.issuetrackingsystem.post.repo.PostRepo;
import com.suman.issuetrackingsystem.user.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {
    @Autowired
    private PostRepo postRepo;

    @Autowired
    private UserRepo userRepo;

    public DashboardDTO getStats() {
        DashboardDTO stats = new DashboardDTO();

        stats.setTotalPosts(postRepo.countByPostStatusNot(PostStatus.DRAFT));
        stats.setPendingApprovalPosts(postRepo.countByPostStatus(PostStatus.PENDING));
        stats.setApprovedPosts(postRepo.countByPostStatus(PostStatus.APPROVED));
        stats.setRejectedPosts(postRepo.countByPostStatus(PostStatus.REJECTED));
        stats.setResolvedPosts(postRepo.countByPostStatus(PostStatus.RESOLVED));

        stats.setTotalUsers(userRepo.count());
        stats.setActiveUsers(userRepo.countByActiveTrue());
        stats.setInactiveUsers(userRepo.countByActiveFalse());

        return stats;
    }
}
