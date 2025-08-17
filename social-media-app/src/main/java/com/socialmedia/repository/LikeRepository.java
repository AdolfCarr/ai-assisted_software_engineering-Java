package com.socialmedia.repository;

import com.socialmedia.model.Like;
import com.socialmedia.model.Post;
import com.socialmedia.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    
    // Find like by user and post
    Optional<Like> findByUserAndPost(User user, Post post);
    
    // Find all likes for a post
    List<Like> findByPost(Post post);
    
    // Find all likes by a user
    List<Like> findByUser(User user);
    
    // Count likes for a post
    long countByPost(Post post);
    
    // Check if a user has liked a post
    boolean existsByUserAndPost(User user, Post post);
    
    // Custom query to check if user liked post
    @Query("SELECT CASE WHEN COUNT(l) > 0 THEN true ELSE false END " +
           "FROM Like l WHERE l.user.id = :userId AND l.post.id = :postId")
    boolean existsByUserIdAndPostId(@Param("userId") Long userId, @Param("postId") Long postId);
    
    // Custom query to get like count for post
    @Query("SELECT COUNT(l) FROM Like l WHERE l.post.id = :postId")
    long countByPostId(@Param("postId") Long postId);
}
