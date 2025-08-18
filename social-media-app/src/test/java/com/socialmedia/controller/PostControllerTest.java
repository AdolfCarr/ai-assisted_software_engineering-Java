package com.socialmedia.controller;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.socialmedia.model.Post;
import com.socialmedia.service.PostService;

@WebMvcTest(PostController.class)
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private PostService postService;

    @Test
    void getPostById() throws Exception {
        Post post = new Post();
        post.setTitle("Test");
        
        when(postService.getPostById(1L)).thenReturn(Optional.of(post));
        
        mockMvc.perform(get("/api/posts/1"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.title").value("Test"));
    }
}
