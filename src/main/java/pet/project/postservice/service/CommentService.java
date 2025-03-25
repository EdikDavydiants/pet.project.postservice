package pet.project.postservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pet.project.postservice.exception.NotFoundException;
import pet.project.postservice.model.dto.CommentDto;
import pet.project.postservice.model.entity.Comment;
import pet.project.postservice.model.entity.Post;
import pet.project.postservice.repository.CommentRepository;
import pet.project.postservice.repository.PostRepository;

import java.time.Instant;

import static pet.project.postservice.constant.ErrorMessagesUtil.NOT_FOUND;
import static pet.project.postservice.constant.ErrorMessagesUtil.combine;
import static pet.project.postservice.mapper.CommentMappers.mapCommentToCommentDto;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    private final PostRepository postRepository;

    public CommentDto createComment(long userId, long postId, String text) {

        Post post = postRepository.findById(postId)
                        .orElseThrow(() -> new NotFoundException(combine("Post", NOT_FOUND)));

        Comment comment = Comment.builder()
                .userId(userId)
                .post(post)
                .text(text)
                .createdAt(Instant.now())
                .build();

        comment = commentRepository.save(comment);
        return mapCommentToCommentDto(comment);
    }
}
