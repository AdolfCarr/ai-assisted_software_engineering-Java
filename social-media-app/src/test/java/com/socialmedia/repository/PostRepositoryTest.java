package com.socialmedia.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.socialmedia.model.Post;
import com.socialmedia.model.User;

@DataJpaTest
@ActiveProfiles("test")
public class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;
    
    @Autowired
    private UserRepository userRepository; // Add this

    @Test
    public void shouldSaveAndRetrievePost() {
        // Given
        User user = new User("testuser", "Test User", "test@email.com", "Bio");
        user = userRepository.save(user); // Save user first
        
        Post post = new Post("Test Title", "Test Content", user);

        // When
        Post saved = postRepository.save(post);

        // Then
        assertThat(postRepository.findById(saved.getId()))
            .isPresent()
            .get()
            .extracting(Post::getTitle)
            .isEqualTo("Test Title");
    }
}

