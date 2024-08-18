package com.costa.luiz.mockito.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Consumer;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User get(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User update(Long id, User user) {
        Consumer<User> saveUser = userRepository::save;
        userRepository
                .findById(id).ifPresentOrElse(saveUser, () -> new IllegalStateException("User not found"));
        return user;
    }

    public User create(User user) {
        return userRepository.save(user);
    }

    public User follow(String userId, String userToFollow) {
        User user = userRepository.findUsersByUserId(userId).orElseThrow(() -> new IllegalStateException("User not found"));
        User userTofollow = userRepository.findUsersByUserId(userToFollow).orElseThrow(() -> new IllegalStateException("User not found"));

        if (!user.getFollowing().contains(userToFollow)) {
            // Adiciona o usuário à lista de seguidos
            user.getFollowing().add(userToFollow);
            // Adiciona este usuário à lista de seguidores do outro usuário
            userTofollow.getFollowers().add(userId);
            userRepository.saveAll(List.of(user, userTofollow));
            log.info(user.getUserId() + " começou a seguir " + userToFollow);
        } else {
            log.info(user.getUserId() + " já está seguindo " + userToFollow);
        }
        return user;

    }

    public void unfollow(Long userId, String followerId) {
        User follower = userRepository.findById(userId).get();
        if (follower.getFollowing().contains(followerId)) {
            // Remove o usuário da lista de seguidos
            follower.getFollowing().remove(followerId);
            // Remove este usuário da lista de seguidores do outro usuário
            User followeer = userRepository.findUsersByUserId(followerId).get();
            followeer.getFollowers().remove(follower.getUserId());
            System.out.println(follower.getName() + " deixou de seguir " + follower.getName());
        } else {
            System.out.println(followerId + " não estava seguindo " + follower.getName());
        }
    }

    public List<User> all() {
        return userRepository.findAll();
    }
}
