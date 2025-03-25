package pet.project.postservice.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import pet.project.postservice.model.entity.Comment;

import java.util.List;


public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findTop10ByPostIdOrderByCreatedAt(Long postId);

    long countByPostId(Long postId);

    Slice<Comment> findByPostId(Long postId, Pageable pageable);
}
