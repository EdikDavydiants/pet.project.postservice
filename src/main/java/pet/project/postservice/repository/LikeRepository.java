package pet.project.postservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pet.project.postservice.model.entity.Like;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    long countByPostId(Long postId);

    boolean existsByPostIdAndUserId(Long postId, Long userId);

    Optional<Like> findByPostIdAndUserId(Long postId, Long userId);
}
