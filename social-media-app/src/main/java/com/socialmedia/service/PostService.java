package com.socialmedia.service;

import com.socialmedia.model.Post;
import com.socialmedia.model.User;
import com.socialmedia.repository.PostRepository;
import com.socialmedia.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private LikeService likeService;
    
    public Post createPost(Long userId, Post post) {
        User author = userRepository.findById(userId).orElseThrow(() -> 
            new RuntimeException("User not found with id: " + userId));
        
        post.setAuthor(author);
        return postRepository.save(post);
    }
    
    public List<Post> getAllPosts() {
        return postRepository.findAllByOrderByCreatedAtDesc();
    }
    
    public Optional<Post> getPostById(Long id) {
        return postRepository.findById(id);
    }
    
    public List<Post> getPostsByUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> 
            new RuntimeException("User not found with id: " + userId));
        
        return postRepository.findByAuthorOrderByCreatedAtDesc(user);
    }
    
    public Post updatePost(Long id, Post postDetails) {
        Post post = postRepository.findById(id).orElseThrow(() -> 
            new RuntimeException("Post not found with id: " + id));
        
        post.setTitle(postDetails.getTitle());
        post.setBody(postDetails.getBody());
        
        return postRepository.save(post);
    }
    
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
    
    public Post likePost(Long userId, Long postId) {
        likeService.likePost(userId, postId);
        return postRepository.findById(postId)
            .orElseThrow(() -> new RuntimeException("Post not found with id: " + postId));
    }
    
    public Post unlikePost(Long userId, Long postId) {
        likeService.unlikePost(userId, postId);
        return postRepository.findById(postId)
            .orElseThrow(() -> new RuntimeException("Post not found with id: " + postId));
    }
}