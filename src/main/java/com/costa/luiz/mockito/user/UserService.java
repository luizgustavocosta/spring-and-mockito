package com.costa.luiz.mockito.user;

import com.costa.luiz.mockito.integration.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    public UserService(UserRepository userRepository, NotificationService notificationService) {
        this.userRepository = userRepository;
        this.notificationService = notificationService;
    }

    public User get(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User create(User newUser) {
        var user = userRepository.save(newUser);
        notificationService.newUser(String.format("User %s has been created", user.getUserId()));
        return user;
    }

    public User follow(String userId, String userToFollow) {
        User user = userRepository.findUsersByUserId(userId).orElseThrow(() -> new IllegalStateException("User not found"));
        User userTofollow = userRepository.findUsersByUserId(userToFollow).orElseThrow(() -> new IllegalStateException("User not found"));

        if (!user.getFollowing().contains(userToFollow)) {
            user.getFollowing().add(userToFollow);
            userTofollow.getFollowers().add(userId);
            userRepository.saveAll(List.of(user, userTofollow));
            log.info(user.getUserId() + " começou a seguir " + userToFollow);
        } else {
            log.info(user.getUserId() + " já está seguindo " + userToFollow);
        }
        return user;

    }

    public User unfollow(String userId, String followerId) {
        User user = userRepository.findUsersByUserId(userId).get();
        if (user.getFollowing().contains(followerId)) {
            user.getFollowing().remove(followerId);
            User followeer = userRepository.findUsersByUserId(followerId).get();
            followeer.getFollowers().remove(user.getUserId());
            userRepository.saveAll(List.of(user, followeer));
            log.info(userId + " deixou de seguir " + followerId);
        } else {
            log.info(userId + " não estava seguindo " + followerId);
        }
        return user;
    }

    public List<User> all() {
        return userRepository.findAll();
    }
}
