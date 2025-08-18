package com.socialmedia.repository;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.socialmedia.model.Like;
import com.socialmedia.model.Post;
import com.socialmedia.model.User;

@DataJpaTest
class LikeRepositoryTest {

    @Autowired
    private TestEntityManager em;
    
    @Autowired
    private LikeRepository likeRepo;
    
    @Test
    void shouldFindByUserAndPost() {
        User user = em.persist(new User("user1", "User", "user@email.com", "Bio"));
        Post post = em.persist(new Post("Test", "Content", user));
        Like like = em.persist(new Like(user, post));
        
        assertThat(likeRepo.findByUserAndPost(user, post))
            .isPresent()
            .get()
            .extracting(Like::getId)
            .isEqualTo(like.getId());
    }
}
