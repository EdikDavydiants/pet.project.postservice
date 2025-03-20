package pet.project.postservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pet.project.postservice.model.dto.PostDto;
import pet.project.postservice.model.dto.request.NewPostDtoRequest;
import pet.project.postservice.model.entity.Post;
import pet.project.postservice.repository.PostRepository;

import static pet.project.postservice.mapper.PostMappers.mapNewPostDtoRequestToPost;
import static pet.project.postservice.mapper.PostMappers.mapPostToPostDto;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public PostDto createPost(long userId, NewPostDtoRequest request) {

        Post newPost = mapNewPostDtoRequestToPost(userId, request);
        newPost = postRepository.save(newPost);
        return mapPostToPostDto(newPost, 0, 0);
    }
}
