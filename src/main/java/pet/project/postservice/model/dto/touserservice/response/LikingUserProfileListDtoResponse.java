package pet.project.postservice.model.dto.touserservice.response;

import pet.project.postservice.model.dto.UserShortProfileDto;

import java.util.List;

public record LikingUserProfileListDtoResponse(

        List<UserShortProfileDto> users
) {
}
