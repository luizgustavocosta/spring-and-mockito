package com.costa.luiz.mockito.post;

import com.costa.luiz.mockito.integration.NotificationService;
import com.costa.luiz.mockito.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    private final NotificationService notificationService;

    public PostService(PostRepository postRepository, UserRepository userRepository, NotificationService notificationService) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.notificationService = notificationService;
    }

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public Post create(String text, String userId) {
        var user = userRepository.findUsersByUserId(userId).orElseThrow(IllegalArgumentException::new);
        Post post = postRepository.save(new Post(text, user));
        validatePost(text);
        return post;
    }

    private void validatePost(String message) {
        notificationService.newPost(message);
    }
}
