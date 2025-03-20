package pet.project.postservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import pet.project.postservice.model.dto.PostDto;
import pet.project.postservice.model.dto.request.NewPostDtoRequest;
import pet.project.postservice.service.PostService;

import static pet.project.postservice.utils.JwtUtils.extractIdFromJwt;
import static pet.project.postservice.utils.JwtUtils.extractTokenFromHeader;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/new")
    public PostDto createPost(@RequestHeader String authHeader, @Valid @RequestBody NewPostDtoRequest request) {

        long user_id = extractIdFromJwt(extractTokenFromHeader(authHeader));
        return postService.createPost(user_id, request);
    }
}
