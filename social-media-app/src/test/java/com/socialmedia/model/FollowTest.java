package com.socialmedia.model;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

class FollowTest {
    
    @Test
    void shouldCreateFollowRelationship() {
        User follower = new User("follower", "Follower", "follower@email.com", "");
        User following = new User("following", "Following", "following@email.com", "");
        
        Follow follow = new Follow(follower, following);
        
        assertThat(follow.getFollower()).isEqualTo(follower);
        assertThat(follow.getFollowing()).isEqualTo(following);
        assertThat(follow.getCreatedAt()).isNotNull();
    }
}