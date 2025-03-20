package pet.project.postservice.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NewPostDtoRequest(

        @NotNull
        @NotBlank
        String content,

        String imageUrl
) {
}
