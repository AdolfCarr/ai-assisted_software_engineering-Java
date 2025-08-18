package com.socialmedia.controller;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.socialmedia.model.Like;
import com.socialmedia.model.Post;
import com.socialmedia.model.User;
import com.socialmedia.service.LikeService;

@WebMvcTest(LikeController.class)
class LikeControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private LikeService likeService;

    @Test
    void shouldLikePost() throws Exception {
    	
    	 User user = new User("user1", "User", "user@email.com", "Bio");
         user.setId(1L);
         Post post = new Post("Test", "Content", user);
         post.setId(1L);
         
        Like like = new Like(
            user, post
        );
        
        given(likeService.likePost(1L, 1L)).willReturn(like);
        
        mockMvc.perform(post("/api/likes/user/1/post/1"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.user.id").value(1));
    }
}
