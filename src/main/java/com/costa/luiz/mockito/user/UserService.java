package com.costa.luiz.mockito.user;

import com.costa.luiz.mockito.integration.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    UserService(UserRepository userRepository, NotificationService notificationService) {
        this.userRepository = userRepository;
        this.notificationService = notificationService;
    }

    User get(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new IllegalStateException("User not found"));
    }

    User create(User newUser) {
        var user = userRepository.save(newUser);
        notificationService.newUser(user);
        return user;
    }

    User follow(String userId, String toFollow) {
        if (userId.equalsIgnoreCase(toFollow)) throw new IllegalArgumentException("User cannot follow itself");

        User user = userRepository.findUsersByUserId(userId)
                .orElseThrow(() -> new IllegalStateException("User not found"));
        User userToFollow = userRepository.findUsersByUserId(toFollow)
                .orElseThrow(() -> new IllegalStateException("User not found"));

        if (!user.getFollowing().contains(toFollow)) {
            user.getFollowing().add(toFollow);
            userToFollow.getFollowers().add(userId);
            userRepository.saveAll(List.of(user, userToFollow));
            log.info("{} started to follow {}", user.getUserId(), toFollow);
        } else {
            log.info("{} already following {}", user.getUserId(), toFollow);
        }
        return user;
    }

    User unfollow(String userId, String followerId) {
        if (userId.equalsIgnoreCase(followerId)) throw new IllegalArgumentException("User cannot unfollow itself");

        User user = userRepository.findUsersByUserId(userId).orElseThrow();
        if (user.getFollowing().contains(followerId)) {
            user.getFollowing().remove(followerId);
            User follower = userRepository.findUsersByUserId(followerId).orElseThrow();
            follower.getFollowers().remove(user.getUserId());
            userRepository.saveAll(List.of(user, follower));
            log.info("{} stopped following {}", userId, followerId);
        } else {
            log.info("{} wasn't following {}", userId, followerId);
        }
        return user;
    }

    List<User> all() {
        return userRepository.findAll();
    }
}
