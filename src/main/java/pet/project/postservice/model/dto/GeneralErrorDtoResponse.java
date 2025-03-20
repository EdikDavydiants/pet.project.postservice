package pet.project.postservice.model.dto;

import lombok.Builder;

@Builder
public record GeneralErrorDtoResponse(

        int status,

        String type,

        String message,

        String details
) {
}
