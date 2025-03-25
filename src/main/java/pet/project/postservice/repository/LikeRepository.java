package pet.project.postservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pet.project.postservice.model.entity.Like;

public interface LikeRepository extends JpaRepository<Like, Long> {

    long countByPostId(Long postId);

    boolean existsByPostIdAndUserId(Long postId, Long userId);
}
