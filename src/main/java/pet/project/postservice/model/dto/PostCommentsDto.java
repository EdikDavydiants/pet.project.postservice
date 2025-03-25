package pet.project.postservice.model.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record PostCommentsDto(

        List<CommentDto> comments,

        Integer count,

        Boolean hasMore
) {
}
