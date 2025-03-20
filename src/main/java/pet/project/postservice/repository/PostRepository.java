package pet.project.postservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pet.project.postservice.model.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {


}
