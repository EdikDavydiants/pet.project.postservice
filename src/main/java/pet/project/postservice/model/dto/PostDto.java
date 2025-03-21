package pet.project.postservice.model.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record PostDto(

        Long id,

        Long authorId,

        String content,

        String imageUrl,

        String createdAt,

        Long likesCount,

        Long commentsCount,

        List<CommentDto> comments
) {
}
