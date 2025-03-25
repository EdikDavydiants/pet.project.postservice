package pet.project.postservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import pet.project.postservice.model.dto.SimpleMessageDto;
import pet.project.postservice.service.LikeService;

import static pet.project.postservice.utils.JwtUtils.extractIdFromJwt;
import static pet.project.postservice.utils.JwtUtils.extractTokenFromHeader;

@RestController
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/{postId}/like")
    public SimpleMessageDto likePost(@RequestHeader(value = "Authorization") String authHeader,
                                     @PathVariable long postId) {

        long userId = extractIdFromJwt(extractTokenFromHeader(authHeader));
        return likeService.likePost(userId, postId);
    }
}
