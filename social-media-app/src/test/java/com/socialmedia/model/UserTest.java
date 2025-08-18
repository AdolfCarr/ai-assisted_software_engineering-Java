package com.socialmedia.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class UserTest {
    
    @Test
    void userCreation() {
        User user = new User("testuser", "Test User", "test@email.com", "Bio");
        
        assertEquals("testuser", user.getUsername());
        assertEquals("Test User", user.getName());
        assertNotNull(user.getCreatedAt());
        assertTrue(user.getPosts().isEmpty());
    }
}

class PostTest {
    
    @Test
    void postCreation() {
        User author = new User("author", "Author", "author@email.com", "Bio");
        Post post = new Post("Title", "Content", author);
        
        assertEquals("Title", post.getTitle());
        assertEquals(author, post.getAuthor());
        assertNull(post.getUpdatedAt());
    }
}