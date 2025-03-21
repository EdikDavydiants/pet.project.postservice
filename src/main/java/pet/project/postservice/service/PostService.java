package pet.project.postservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pet.project.postservice.exception.PostNotFoundException;
import pet.project.postservice.model.dto.PostDto;
import pet.project.postservice.model.dto.request.NewPostDtoRequest;
import pet.project.postservice.model.entity.Comment;
import pet.project.postservice.model.entity.Post;
import pet.project.postservice.repository.CommentRepository;
import pet.project.postservice.repository.LikeRepository;
import pet.project.postservice.repository.PostRepository;

import java.util.List;

import static pet.project.postservice.constant.ErrorMessagesUtil.POST_NOT_FOUND;
import static pet.project.postservice.mapper.PostMappers.mapNewPostDtoRequestToPost;
import static pet.project.postservice.mapper.PostMappers.mapPostToPostDto;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;

    public PostDto createPost(long userId, NewPostDtoRequest request) {

        Post newPost = mapNewPostDtoRequestToPost(userId, request);
        newPost = postRepository.save(newPost);
        return mapPostToPostDto(newPost, 0, 0, null);
    }

    public PostDto getPost(long id) {

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(POST_NOT_FOUND));

        List<Comment> comments = commentRepository.findTop10ByPostIdOrderByCreatedAt(id);

        long likesCount = likeRepository.countByPostId(id);
        long commentsCount = commentRepository.countByPostId(id);

        return mapPostToPostDto(post, likesCount, commentsCount, comments);
    }
}
