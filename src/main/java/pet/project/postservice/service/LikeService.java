package pet.project.postservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pet.project.postservice.exception.NotFoundException;
import pet.project.postservice.exception.PostNotFoundException;
import pet.project.postservice.exception.ResponseException;
import pet.project.postservice.model.dto.PostLikesDto;
import pet.project.postservice.model.dto.SimpleMessageDto;
import pet.project.postservice.model.dto.touserservice.request.LikingUserIdListDtoRequest;
import pet.project.postservice.model.dto.touserservice.response.LikingUserProfileListDtoResponse;
import pet.project.postservice.model.entity.Like;
import pet.project.postservice.model.entity.Post;
import pet.project.postservice.repository.LikeRepository;
import pet.project.postservice.repository.PostRepository;

import java.time.Instant;

import static pet.project.postservice.constant.ErrorMessagesUtil.ALREADY_EXISTS;
import static pet.project.postservice.constant.ErrorMessagesUtil.EMPTY_RESPONSE;
import static pet.project.postservice.constant.ErrorMessagesUtil.NOT_FOUND;
import static pet.project.postservice.constant.ErrorMessagesUtil.SUCCESSFUL_DELETING;
import static pet.project.postservice.constant.ErrorMessagesUtil.SUCCESSFUL_SAVING;
import static pet.project.postservice.constant.ErrorMessagesUtil.combine;

@Service
@RequiredArgsConstructor
public class LikeService {

    private static final int PAGE_SIZE = 10;

    private final LikeRepository likeRepository;

    private final PostRepository postRepository;

    private final RestTemplate restTemplate;

    @Value("${service.user.url}")
    private String userServiceUrl;

    public SimpleMessageDto likePost(long userId, long postId) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(combine("Post", NOT_FOUND)));

        if (likeRepository.existsByPostIdAndUserId(postId, userId)) {
            return new SimpleMessageDto(combine("Like", ALREADY_EXISTS));
        }

        Like newLike = Like.builder()
                .post(post)
                .userId(userId)
                .createdAt(Instant.now())
                .build();

        likeRepository.save(newLike);
        return new SimpleMessageDto(combine("Like", SUCCESSFUL_SAVING));
    }

    public SimpleMessageDto removeLikeFromPost(long userId, long postId) {

        Like like = likeRepository.findByPostIdAndUserId(postId, userId)
                .orElseThrow(() -> new NotFoundException(combine("Like", NOT_FOUND)));

        likeRepository.delete(like);
        return new SimpleMessageDto(combine("Like", SUCCESSFUL_DELETING));
    }

    public PostLikesDto getPostLikes(long postId, int page) {

        if(!postRepository.existsById(postId)) {
            throw new NotFoundException(combine("Post", NOT_FOUND));
        }

        Slice<Long> userIds = likeRepository.findUserIdsByPostId(
                postId, PageRequest.of(page, PAGE_SIZE));

        var likingUserIdListEntityRequest = new HttpEntity<>(new LikingUserIdListDtoRequest(userIds.getContent()));
        ResponseEntity<LikingUserProfileListDtoResponse> usResponse = restTemplate.exchange(
                        userServiceUrl + "/likes",
                        HttpMethod.POST,
                        likingUserIdListEntityRequest,
                        LikingUserProfileListDtoResponse.class);

        if (usResponse.getBody() == null) {
            throw new ResponseException(EMPTY_RESPONSE);
        }

        return PostLikesDto.builder()
                .users(usResponse.getBody().users())
                .hasMore(userIds.hasNext())
                .build();
    }
}
