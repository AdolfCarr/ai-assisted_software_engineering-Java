package com.socialmedia.service;

import com.socialmedia.model.Follow;
import com.socialmedia.model.User;
import com.socialmedia.repository.FollowRepository;
import com.socialmedia.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FollowService {
    
    @Autowired
    private FollowRepository followRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Transactional
    public Follow followUser(Long followerId, Long followingId) {
        User follower = userRepository.findById(followerId)
            .orElseThrow(() -> new RuntimeException("User not found with id: " + followerId));
        
        User following = userRepository.findById(followingId)
            .orElseThrow(() -> new RuntimeException("User not found with id: " + followingId));
        
        if (followerId.equals(followingId)) {
            throw new IllegalArgumentException("Cannot follow yourself");
        }
        
        if (followRepository.existsByFollowerAndFollowing(follower, following)) {
            throw new IllegalStateException("Already following this user");
        }
        
        Follow follow = new Follow(follower, following);
        return followRepository.save(follow);
    }
    
    @Transactional
    public void unfollowUser(Long followerId, Long followingId) {
        User follower = userRepository.findById(followerId)
            .orElseThrow(() -> new RuntimeException("User not found with id: " + followerId));
        
        User following = userRepository.findById(followingId)
            .orElseThrow(() -> new RuntimeException("User not found with id: " + followingId));
        
        Follow follow = followRepository.findByFollowerAndFollowing(follower, following)
            .orElseThrow(() -> new IllegalStateException("Follow relationship not found"));
        
        followRepository.delete(follow);
    }
    
    public boolean isFollowing(Long followerId, Long followingId) {
        return followRepository.isFollowing(followerId, followingId);
    }
    
    public long getFollowerCount(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        return followRepository.countByFollowing(user);
    }
    
    public long getFollowingCount(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        return followRepository.countByFollower(user);
    }
}