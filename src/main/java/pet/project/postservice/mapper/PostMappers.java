package pet.project.postservice.mapper;

import pet.project.postservice.model.dto.PostDto;
import pet.project.postservice.model.dto.request.NewPostDtoRequest;
import pet.project.postservice.model.entity.Post;

import java.time.Instant;

public class PostMappers {

    public static PostDto mapPostToPostDto(Post post, int likesCount, int commentsCount) {

        return PostDto.builder()
                .id(post.getId())
                .authorId(post.getAuthorId())
                .content(post.getContent())
                .imageUrl(post.getImageUrl())
                .createdAt(post.getCreatedAt().toString())
                .likesCount(likesCount)
                .commentsCount(commentsCount)
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
