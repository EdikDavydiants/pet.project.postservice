package pet.project.postservice.model.dto.touserservice.request;

import java.util.List;

public record LikingUserIdListDtoRequest(

        List<Long> userIds
) {
}
