package pet.project.postservice.model.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record PostLikesDto(

        List<UserShortProfileDto> users,

        Boolean hasMore
) {
}
