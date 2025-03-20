package pet.project.postservice.model.dto;

import lombok.Builder;

@Builder
public record PostDto(

        Long id,

        Long authorId,

        String content,

        String imageUrl,

        String createdAt,

        int likesCount,

        int commentsCount
) {
}
