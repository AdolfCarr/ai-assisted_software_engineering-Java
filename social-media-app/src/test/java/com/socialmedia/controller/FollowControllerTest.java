package com.socialmedia.controller;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.socialmedia.model.Follow;
import com.socialmedia.model.User;
import com.socialmedia.service.FollowService;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FollowController.class)
class FollowControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private FollowService followService;

    @Test
    void shouldCreateFollow() throws Exception {
        User follower = new User();
        follower.setId(1L);
        
        User following = new User();
        following.setId(2L);
        
        given(followService.followUser(1L, 2L))
            .willReturn(new Follow(follower, following));
    }
    
    @Test
    void shouldReturnConflictWhenAlreadyFollowing() throws Exception {
        given(followService.followUser(1L, 2L))
            .willThrow(new IllegalStateException("Already following"));
        
        mockMvc.perform(post("/api/follows/follower/1/following/2"))
               .andExpect(status().isBadRequest());
    }
    
    @Test
    void shouldGetFollowerCount() throws Exception {
        given(followService.getFollowerCount(1L)).willReturn(5L);
        
        mockMvc.perform(get("/api/follows/user/1/followers/count"))
               .andExpect(status().isOk())
               .andExpect(content().string("5"));
    }
}
