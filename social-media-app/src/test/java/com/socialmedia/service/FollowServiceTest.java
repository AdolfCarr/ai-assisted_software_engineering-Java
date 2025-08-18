package com.socialmedia.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.socialmedia.model.User;
import com.socialmedia.repository.FollowRepository;
import com.socialmedia.repository.UserRepository;

import static org.mockito.Mockito.*;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class FollowServiceTest {

    @Mock
    private FollowRepository followRepo;
    
    @Mock
    private UserRepository userRepo;
    
    @InjectMocks
    private FollowService followService;
    
    @Test
    void shouldPreventDuplicateFollow() {
        User follower = new User();
        follower.setId(1L);
        User following = new User();
        following.setId(2L);
        
        when(userRepo.findById(1L)).thenReturn(Optional.of(follower));
        when(userRepo.findById(2L)).thenReturn(Optional.of(following));
        when(followRepo.existsByFollowerAndFollowing(follower, following)).thenReturn(true);
        
        assertThatThrownBy(() -> followService.followUser(1L, 2L))
            .isInstanceOf(IllegalStateException.class)
            .hasMessage("Already following this user");
    }
}
