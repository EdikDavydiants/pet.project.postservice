package pet.project.postservice.model.dto;

public record UserShortProfileDto(

        Long id,

        String name,

        String avatar,

        String bio
) {
}
