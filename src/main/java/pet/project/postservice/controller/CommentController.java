package pet.project.postservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import pet.project.postservice.model.dto.CommentDto;
import pet.project.postservice.model.dto.request.NewCommentDtoRequest;
import pet.project.postservice.service.CommentService;

import static pet.project.postservice.utils.JwtUtils.extractIdFromJwt;
import static pet.project.postservice.utils.JwtUtils.extractTokenFromHeader;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{postId}/comments/new")
    public CommentDto createComment(@RequestHeader("Authorization") String authHeader,
                                    @PathVariable long postId,
                                    @Valid @RequestBody NewCommentDtoRequest request) {

        long userId = extractIdFromJwt(extractTokenFromHeader(authHeader));
        return commentService.createComment(userId, postId, request.text());
    }
}
