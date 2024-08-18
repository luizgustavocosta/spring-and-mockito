package com.costa.luiz.mockito.post;

import com.costa.luiz.mockito.integration.RabbitService;
import com.costa.luiz.mockito.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    private final RabbitService rabbitService;

    public PostService(PostRepository postRepository, UserRepository userRepository, RabbitService rabbitService) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.rabbitService = rabbitService;
    }

    public Post findById(Long id) {
        return postRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public Post create(String text, String userId) {
        var user = userRepository.findUsersByUserId(userId).orElseThrow(IllegalArgumentException::new);
        return postRepository.save(new Post(text, user));
    }

    public void sendToRabbit() {
        rabbitService.sendMessage("message");
    }
}
