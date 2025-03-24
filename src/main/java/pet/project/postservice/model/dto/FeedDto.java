package pet.project.postservice.model.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record FeedDto(

        List<PostDto> posts,

        Integer page,

        Boolean hasMore,

        Integer size
) {
}
