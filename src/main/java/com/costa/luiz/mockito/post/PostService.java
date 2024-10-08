package com.costa.luiz.mockito.post;

import com.costa.luiz.mockito.integration.NotificationService;
import com.costa.luiz.mockito.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    private final NotificationService notificationService;

    PostService(PostRepository postRepository, UserRepository userRepository, NotificationService notificationService) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.notificationService = notificationService;
    }

    List<Post> findAll() {
        return postRepository.findAll();
    }

    Post create(String text, String userId) {
        var user = userRepository.findUsersByUserId(userId).orElseThrow(IllegalArgumentException::new);
        Post post = postRepository.save(new Post(text, user));
        validatePost(text);
        return post;
    }

    void validatePost(String message) {
        notificationService.newPost(message);
    }
}
