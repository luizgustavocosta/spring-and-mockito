package com.costa.luiz.mockito;

import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public Post findById(Long id) {
        return postRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public Post create(String text, String userId) {
        var user = userRepository.findUsersByUserId(userId).orElseThrow(IllegalArgumentException::new);
        var post = postRepository.save(new Post(text, user));
        return post;
    }
}
