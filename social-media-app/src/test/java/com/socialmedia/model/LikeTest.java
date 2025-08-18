package com.socialmedia.model;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

class LikeTest {
    
    @Test
    void shouldCreateLike() {
        User user = new User("testuser", "Test User", "test@email.com", "Bio");
        Post post = new Post("Test", "Content", user);
        Like like = new Like(user, post);
        
        assertThat(like.getUser()).isEqualTo(user);
        assertThat(like.getPost()).isEqualTo(post);
        assertThat(like.getCreatedAt()).isBeforeOrEqualTo(LocalDateTime.now());
    }
}
