package pet.project.postservice.repository;

import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pet.project.postservice.model.entity.Like;

import org.springframework.data.domain.Pageable;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    long countByPostId(Long postId);

    boolean existsByPostIdAndUserId(Long postId, Long userId);

    Optional<Like> findByPostIdAndUserId(Long postId, Long userId);

    @Query("""
        SELECT l.userId From Like l
        WHERE l.post.id = :postId
        ORDER BY l.createdAt DESC
        """)
    Slice<Long> findUserIdsByPostId(Long postId, Pageable pageable);
}
