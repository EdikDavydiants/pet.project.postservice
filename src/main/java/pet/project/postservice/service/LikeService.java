package pet.project.postservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pet.project.postservice.exception.PostNotFoundException;
import pet.project.postservice.model.dto.SimpleMessageDto;
import pet.project.postservice.model.entity.Like;
import pet.project.postservice.model.entity.Post;
import pet.project.postservice.repository.LikeRepository;
import pet.project.postservice.repository.PostRepository;

import java.time.Instant;

import static pet.project.postservice.constant.ErrorMessagesUtil.ALREADY_EXISTS;
import static pet.project.postservice.constant.ErrorMessagesUtil.NOT_FOUND;
import static pet.project.postservice.constant.ErrorMessagesUtil.SUCCESSFUL_SAVING;
import static pet.project.postservice.constant.ErrorMessagesUtil.combine;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final PostRepository postRepository;

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
}
