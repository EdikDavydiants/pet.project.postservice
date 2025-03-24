package pet.project.postservice.model.dto.touserservice.response;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record AllFriendIdListDtoResponse(

        @NotNull
        List<Long> friendIdList
) {
}
