package com.socialmedia.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.socialmedia.model.Like;
import com.socialmedia.model.Post;
import com.socialmedia.model.User;
import com.socialmedia.repository.LikeRepository;
import com.socialmedia.repository.PostRepository;
import com.socialmedia.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class LikeServiceTest {

    @Mock
    private LikeRepository likeRepo;
    
    @Mock
    private UserRepository userRepo;
    
    @Mock
    private PostRepository postRepo;
    
    @InjectMocks
    private LikeService likeService;

    @Test
    void shouldLikePost() {
        User user = new User("user1", "User", "user@email.com", "Bio");
        user.setId(1L);
        Post post = new Post("Test", "Content", user);
        post.setId(1L);
        
        when(userRepo.findById(1L)).thenReturn(Optional.of(user));
        when(postRepo.findById(1L)).thenReturn(Optional.of(post));
        when(likeRepo.existsByUserAndPost(user, post)).thenReturn(false);
        when(likeRepo.save(any())).thenAnswer(inv -> inv.getArgument(0));
        
        Like result = likeService.likePost(1L, 1L);
        
        assertThat(result.getUser()).isEqualTo(user);
        assertThat(result.getPost()).isEqualTo(post);
    }
}
