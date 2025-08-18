package com.socialmedia.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.socialmedia.model.Post;
import com.socialmedia.model.User;
import com.socialmedia.repository.PostRepository;
import com.socialmedia.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    private PostRepository postRepo;
    
    @Mock
    private UserRepository userRepo;
    
    @InjectMocks
    private PostService postService;

    @Test
    void createPost() {
        User user = new User("user", "User", "user@email.com", "Bio");
        Post post = new Post("Test", "Content", user);
        
        when(userRepo.findById(1L)).thenReturn(Optional.of(user));
        when(postRepo.save(any())).thenReturn(post);
        
        Post created = postService.createPost(1L, post);
        
        assertEquals("Test", created.getTitle());
        verify(postRepo).save(post);
    }
}
