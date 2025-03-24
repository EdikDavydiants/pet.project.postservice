package pet.project.postservice.mapper;

import pet.project.postservice.model.dto.CommentDto;
import pet.project.postservice.model.dto.FeedDto;
import pet.project.postservice.model.dto.PostDto;
import pet.project.postservice.model.dto.request.NewPostDtoRequest;
import pet.project.postservice.model.entity.Comment;
import pet.project.postservice.model.entity.Post;

import java.time.Instant;
import java.util.List;

public class PostMappers {

    public static PostDto mapPostToPostDto(Post post, long likesCount, long commentsCount, List<Comment> comments) {

        List<CommentDto> commentDtoList = null;
        if (comments != null) {
            commentDtoList = CommentMappers.mapCommentListToCommentDtoList(comments);
        }
        return PostDto.builder()
                .id(post.getId())
                .authorId(post.getAuthorId())
                .content(post.getContent())
                .imageUrl(post.getImageUrl())
                .createdAt(post.getCreatedAt().toString())
                .likesCount(likesCount)
                .commentsCount(commentsCount)
                .comments(commentDtoList)
                .build();
    }

    public static Post mapNewPostDtoRequestToPost(long userId, NewPostDtoRequest request) {

        return Post.builder()
                .authorId(userId)
                .content(request.content())
                .imageUrl(request.imageUrl())
                .createdAt(Instant.now())
                .build();
    }
}
