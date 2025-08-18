package com.socialmedia.service;

import com.socialmedia.model.Like;
import com.socialmedia.model.Post;
import com.socialmedia.model.User;
import com.socialmedia.repository.LikeRepository;
import com.socialmedia.repository.PostRepository;
import com.socialmedia.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class LikeService {
    
    @Autowired
    private LikeRepository likeRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PostRepository postRepository;
    
    @Transactional
    public Like likePost(Long userId, Long postId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        
        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new RuntimeException("Post not found with id: " + postId));
        
        if (likeRepository.existsByUserAndPost(user, post)) {
            throw new IllegalStateException("User already liked this post");
        }
        
        Like like = new Like(user, post);
        return likeRepository.save(like);
    }
    
    @Transactional
    public void unlikePost(Long userId, Long postId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        
        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new RuntimeException("Post not found with id: " + postId));
        
        Like like = likeRepository.findByUserAndPost(user, post)
            .orElseThrow(() -> new IllegalStateException("Like not found"));
        
        likeRepository.delete(like);
    }
    
    public List<Like> getLikesByPost(Long postId) {
        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new RuntimeException("Post not found with id: " + postId));
        return likeRepository.findByPost(post);
    }
    
    public List<Like> getLikesByUser(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        return likeRepository.findByUser(user);
    }
    
    public long getLikeCount(Long postId) {
        return likeRepository.countByPostId(postId);
    }
    
    public boolean hasUserLikedPost(Long userId, Long postId) {
        return likeRepository.existsByUserIdAndPostId(userId, postId);
    }
}