package pet.project.postservice.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NewCommentDtoRequest(

        @NotNull
        @NotBlank
        String text
) {
}
