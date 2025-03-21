package pet.project.postservice.model.dto;

import lombok.Builder;

@Builder
public record CommentDto(

        Long id,

        Long postId,

        Long authorId,

        String text,

        String createdAt
) {
}
