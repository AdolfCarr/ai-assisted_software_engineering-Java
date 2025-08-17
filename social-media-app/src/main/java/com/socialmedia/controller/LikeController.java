package com.socialmedia.controller;

import com.socialmedia.model.Like;
import com.socialmedia.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/likes")
public class LikeController {
    
    @Autowired
    private LikeService likeService;
    
    @PostMapping("/user/{userId}/post/{postId}")
    public ResponseEntity<Like> likePost(
            @PathVariable Long userId,
            @PathVariable Long postId) {
        try {
            Like like = likeService.likePost(userId, postId);
            return ResponseEntity.ok(like);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    
    @DeleteMapping("/user/{userId}/post/{postId}")
    public ResponseEntity<Void> unlikePost(
            @PathVariable Long userId,
            @PathVariable Long postId) {
        try {
            likeService.unlikePost(userId, postId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/post/{postId}")
    public ResponseEntity<List<Like>> getLikesByPost(@PathVariable Long postId) {
        try {
            List<Like> likes = likeService.getLikesByPost(postId);
            return ResponseEntity.ok(likes);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Like>> getLikesByUser(@PathVariable Long userId) {
        try {
            List<Like> likes = likeService.getLikesByUser(userId);
            return ResponseEntity.ok(likes);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/post/{postId}/count")
    public ResponseEntity<Long> getLikeCount(@PathVariable Long postId) {
        try {
            long count = likeService.getLikeCount(postId);
            return ResponseEntity.ok(count);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/user/{userId}/post/{postId}/exists")
    public ResponseEntity<Boolean> hasUserLikedPost(
            @PathVariable Long userId,
            @PathVariable Long postId) {
        try {
            boolean exists = likeService.hasUserLikedPost(userId, postId);
            return ResponseEntity.ok(exists);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}