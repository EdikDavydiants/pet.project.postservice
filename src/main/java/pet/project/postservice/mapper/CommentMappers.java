package pet.project.postservice.mapper;

import pet.project.postservice.model.dto.CommentDto;
import pet.project.postservice.model.entity.Comment;

import java.util.List;

public class CommentMappers {

    public static CommentDto mapCommentToCommentDto(Comment comment) {

        return CommentDto.builder()
                .id(comment.getId())
                .postId(comment.getPost().getId())
                .authorId(comment.getUserId())
                .text(comment.getText())
                .createdAt(comment.getCreatedAt().toString())
                .build();
    }

    public static List<CommentDto> mapCommentListToCommentDtoList(List<Comment> comments) {

        return comments.stream()
                .map(CommentMappers::mapCommentToCommentDto)
                .toList();
    }
}
