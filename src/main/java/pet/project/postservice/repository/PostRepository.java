package pet.project.postservice.repository;

import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pet.project.postservice.model.dto.PostDto;
import pet.project.postservice.model.entity.Post;

import org.springframework.data.domain.Pageable;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(
        value = """
            SELECT
                NEW pet.project.postservice.model.dto.PostDto
                (
                    p.id,
                    p.authorId,
                    p.content,
                    p.imageUrl,
                    p.createdAt,
                    (SELECT COUNT(l) FROM Post p2 LEFT JOIN p2.likes l WHERE p2 = p),
                    (SELECT COUNT(c) FROM Post p3 LEFT JOIN p3.comments c WHERE p3 = p),
                    NULL
                )
            FROM Post p
            WHERE p.authorId IN :authorIds
            ORDER BY p.createdAt DESC
            """)
    Slice<PostDto> getFeed(@Param("authorIds") List<Long> authorIds, Pageable pageable);
}
