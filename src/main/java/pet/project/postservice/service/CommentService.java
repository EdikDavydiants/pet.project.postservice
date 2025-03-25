package pet.project.postservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pet.project.postservice.exception.ActionNotAllowedException;
import pet.project.postservice.exception.NotFoundException;
import pet.project.postservice.model.dto.CommentDto;
import pet.project.postservice.model.dto.PostCommentsDto;
import pet.project.postservice.model.dto.SimpleMessageDto;
import pet.project.postservice.model.entity.Comment;
import pet.project.postservice.model.entity.Post;
import pet.project.postservice.repository.CommentRepository;
import pet.project.postservice.repository.PostRepository;

import java.time.Instant;
import java.util.List;

import static pet.project.postservice.constant.ErrorMessagesUtil.FORBIDDEN_REQUEST;
import static pet.project.postservice.constant.ErrorMessagesUtil.NOT_FOUND;
import static pet.project.postservice.constant.ErrorMessagesUtil.SUCCESSFUL_DELETING;
import static pet.project.postservice.constant.ErrorMessagesUtil.combine;
import static pet.project.postservice.mapper.CommentMappers.mapCommentListToCommentDtoList;
import static pet.project.postservice.mapper.CommentMappers.mapCommentToCommentDto;

@Service
@RequiredArgsConstructor
public class CommentService {

    private static final int PAGE_SIZE = 10;

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

    public PostCommentsDto getPostComments(long postId, int page) {

        if (!postRepository.existsById(postId)) {
            throw new NotFoundException(combine("Post", NOT_FOUND));
        }

        Slice<Comment> commentsSlice = commentRepository.findByPostId(
                postId, PageRequest.of(page, PAGE_SIZE, Sort.by("createdAt").descending()));

        List<CommentDto> commentDtoList = mapCommentListToCommentDtoList(commentsSlice.getContent());

        return PostCommentsDto.builder()
                .hasMore(commentsSlice.hasNext())
                .comments(commentDtoList)
                .count(commentDtoList.size())
                .build();
    }

    public SimpleMessageDto deleteComment(long userId, long commentId) {

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException(combine("Comment", NOT_FOUND)));

        long postAuthorId = comment.getPost().getAuthorId();
        long commentAuthorId = comment.getUserId();

        if (userId != postAuthorId || userId != commentAuthorId) {
            throw new ActionNotAllowedException(FORBIDDEN_REQUEST);
        }

        commentRepository.delete(comment);
        return new SimpleMessageDto(combine("Comment", SUCCESSFUL_DELETING));
    }
}
