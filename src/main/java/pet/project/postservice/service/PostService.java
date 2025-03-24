package pet.project.postservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import pet.project.postservice.exception.ActionNotAllowedException;
import pet.project.postservice.exception.NotFoundException;
import pet.project.postservice.exception.PostNotFoundException;
import pet.project.postservice.exception.ResponseException;
import pet.project.postservice.exception.UnknownException;
import pet.project.postservice.model.dto.FeedDto;
import pet.project.postservice.model.dto.PostDto;
import pet.project.postservice.model.dto.SimpleMessageDto;
import pet.project.postservice.model.dto.request.NewPostDtoRequest;
import pet.project.postservice.model.dto.touserservice.response.AllFriendIdListDtoResponse;
import pet.project.postservice.model.entity.Comment;
import pet.project.postservice.model.entity.Post;
import pet.project.postservice.repository.CommentRepository;
import pet.project.postservice.repository.LikeRepository;
import pet.project.postservice.repository.PostRepository;

import java.util.List;
import java.util.Optional;

import static pet.project.postservice.constant.ErrorMessagesUtil.EMPTY_RESPONSE;
import static pet.project.postservice.constant.ErrorMessagesUtil.FORBIDDEN_REQUEST;
import static pet.project.postservice.constant.ErrorMessagesUtil.NOT_FOUND;
import static pet.project.postservice.constant.ErrorMessagesUtil.SUCCESSFUL_DELETING;
import static pet.project.postservice.constant.ErrorMessagesUtil.UNKNOWN_ERROR;
import static pet.project.postservice.constant.ErrorMessagesUtil.combine;
import static pet.project.postservice.mapper.PostMappers.mapNewPostDtoRequestToPost;
import static pet.project.postservice.mapper.PostMappers.mapPostToPostDto;

@Service
@RequiredArgsConstructor
public class PostService {

    private static final int PAGE_SIZE = 15;

    private final PostRepository postRepository;

    private final CommentRepository commentRepository;

    private final LikeRepository likeRepository;

    private final RestTemplate restTemplate;

    @Value("${service.user.url}")
    private String userServiceUrl;

    public PostDto createPost(long userId, NewPostDtoRequest request) {

        Post newPost = mapNewPostDtoRequestToPost(userId, request);
        newPost = postRepository.save(newPost);
        return mapPostToPostDto(newPost, 0, 0, null);
    }

    public PostDto getPost(long id) {

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(combine("Post", NOT_FOUND)));

        List<Comment> comments = commentRepository.findTop10ByPostIdOrderByCreatedAt(id);

        long likesCount = likeRepository.countByPostId(id);
        long commentsCount = commentRepository.countByPostId(id);

        return mapPostToPostDto(post, likesCount, commentsCount, comments);
    }

    public SimpleMessageDto deletePost(long userId, long postId) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(combine("Post", NOT_FOUND)));

        if(post.getAuthorId() != userId) {
            throw new ActionNotAllowedException(FORBIDDEN_REQUEST);
        }

        postRepository.delete(post);
        return new SimpleMessageDto(combine("Post", SUCCESSFUL_DELETING));
    }

    public FeedDto getFeed(long userId, int page) {

        Optional<AllFriendIdListDtoResponse> usFriendIdListResponseOpt;
        try {
            usFriendIdListResponseOpt = Optional.ofNullable(restTemplate.getForObject(
                    userServiceUrl + "/" + userId + "/friendsIds", AllFriendIdListDtoResponse.class));
        } catch (HttpClientErrorException e) {
            throw new NotFoundException(combine("User", NOT_FOUND));
        } catch (RestClientException e) {
            throw new UnknownException(UNKNOWN_ERROR);
        }

        List<Long> friendIdList = usFriendIdListResponseOpt
                .orElseThrow(() -> new ResponseException(EMPTY_RESPONSE))
                .friendIdList();

        Slice<PostDto> postDtoSlice = postRepository.getFeed(
                friendIdList, PageRequest.of(page, PAGE_SIZE, Sort.by("CreatedAt").descending()));

        return FeedDto.builder()
                .posts(postDtoSlice.getContent())
                .page(postDtoSlice.getNumber())
                .size(postDtoSlice.getNumberOfElements())
                .hasMore(postDtoSlice.hasNext())
                .build();
    }
}
