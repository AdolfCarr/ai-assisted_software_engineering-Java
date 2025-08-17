package com.socialmedia.controller;

import com.socialmedia.model.Follow;
import com.socialmedia.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/follows")
public class FollowController {
    
    @Autowired
    private FollowService followService;
    
    @PostMapping("/follower/{followerId}/following/{followingId}")
    public ResponseEntity<Follow> followUser(
            @PathVariable Long followerId,
            @PathVariable Long followingId) {
        try {
            Follow follow = followService.followUser(followerId, followingId);
            return ResponseEntity.ok(follow);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    
    @DeleteMapping("/follower/{followerId}/following/{followingId}")
    public ResponseEntity<Void> unfollowUser(
            @PathVariable Long followerId,
            @PathVariable Long followingId) {
        try {
            followService.unfollowUser(followerId, followingId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/follower/{followerId}/following/{followingId}/check")
    public ResponseEntity<Boolean> checkFollowing(
            @PathVariable Long followerId,
            @PathVariable Long followingId) {
        try {
            boolean isFollowing = followService.isFollowing(followerId, followingId);
            return ResponseEntity.ok(isFollowing);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/user/{userId}/followers/count")
    public ResponseEntity<Long> getFollowerCount(@PathVariable Long userId) {
        try {
            long count = followService.getFollowerCount(userId);
            return ResponseEntity.ok(count);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/user/{userId}/following/count")
    public ResponseEntity<Long> getFollowingCount(@PathVariable Long userId) {
        try {
            long count = followService.getFollowingCount(userId);
            return ResponseEntity.ok(count);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}