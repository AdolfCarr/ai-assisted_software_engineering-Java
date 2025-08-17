package com.socialmedia.repository;

import com.socialmedia.model.Follow;
import com.socialmedia.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    
    // Check if a follow relationship exists
    boolean existsByFollowerAndFollowing(User follower, User following);
    
    // Find specific follow relationship
    Optional<Follow> findByFollowerAndFollowing(User follower, User following);
    
    // Count followers for a user
    long countByFollowing(User following);
    
    // Count users being followed by a user
    long countByFollower(User follower);
    
    // Custom query to check if user1 follows user2
    @Query("SELECT CASE WHEN COUNT(f) > 0 THEN true ELSE false END " +
           "FROM Follow f WHERE f.follower.id = :followerId AND f.following.id = :followingId")
    boolean isFollowing(@Param("followerId") Long followerId, @Param("followingId") Long followingId);
}