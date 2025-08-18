package com.socialmedia.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import com.socialmedia.model.Follow;
import com.socialmedia.model.User;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class FollowRepositoryTest {

    @Autowired
    private FollowRepository followRepo;
    
    @Autowired
    private UserRepository userRepo;
    
    @Autowired
    private TestEntityManager entityManager;

    @Test
    void shouldSaveFollowRelationship() {
        User follower = userRepo.save(new User("follower", "Follower", "f1@email.com", ""));
        User following = userRepo.save(new User("following", "Following", "f2@email.com", ""));
        
        Follow follow = followRepo.save(new Follow(follower, following));
        
        assertThat(followRepo.findById(follow.getId())).isPresent();
        assertThat(followRepo.existsByFollowerAndFollowing(follower, following)).isTrue();
    }
    
    @Test
    void shouldCountFollows() {
        User user1 = userRepo.save(new User("user1", "User 1", "u1@email.com", ""));
        User user2 = userRepo.save(new User("user2", "User 2", "u2@email.com", ""));
        
        followRepo.save(new Follow(user1, user2));
        followRepo.save(new Follow(user2, user1));
        
        assertThat(followRepo.countByFollower(user1)).isEqualTo(1);
        assertThat(followRepo.countByFollowing(user1)).isEqualTo(1);
    }
}
