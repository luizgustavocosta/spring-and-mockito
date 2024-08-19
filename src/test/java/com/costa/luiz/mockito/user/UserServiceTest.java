package com.costa.luiz.mockito.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    // User successfully follows another user
    @Test
    void test_user_successfully_follows_another_user() {
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        UserService userService = new UserService(userRepository, null);

        User user = new User("user1", "User One");
        User userToFollow = new User("user2", "User Two");

        when(userRepository.findUsersByUserId("user1")).thenReturn(Optional.of(user));
        when(userRepository.findUsersByUserId("user2")).thenReturn(Optional.of(userToFollow));

        User result = userService.follow("user1", "user2");

        Assertions.assertTrue(result.getFollowing().contains("user2"));
        Assertions.assertTrue(userToFollow.getFollowers().contains("user1"));
        Mockito.verify(userRepository).saveAll(List.of(user, userToFollow));
    }

    // User not found in the repository
    @Test
    void test_user_not_found_in_repository() {
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        UserService userService = new UserService(userRepository, null);

        when(userRepository.findUsersByUserId("user1")).thenReturn(Optional.empty());

        Exception exception = Assertions.assertThrows(IllegalStateException.class, () -> {
            userService.follow("user1", "user2");
        });

        Assertions.assertEquals("User not found", exception.getMessage());
    }

    // User tries to unfollow a non-existent user
    @Test
    public void test_user_tries_to_unfollow_non_existent_user() {
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        UserService userService = new UserService(userRepository, null);

        User user = new User("user1", "User One");
        when(userRepository.findUsersByUserId("user1")).thenReturn(Optional.of(user));

        userService.unfollow(user.getUserId(), "nonExistentUser");

        assertFalse(user.getFollowing().contains("nonExistentUser"));
    }

    @Mock
    UserRepository userRepository;

    // User successfully unfollows another user
    @Test
    public void test_user_successfully_unfollows_another_user() {
        UserService userService = new UserService(userRepository, null);

        User user = new User("user1", "User One");
        User follower = new User("user2", "User Two");
        when(userRepository.findUsersByUserId(anyString()))
                .thenReturn(Optional.of(user))
                .thenReturn(Optional.of(follower));

        user.getFollowing().add(follower.getUserId());
        follower.getFollowers().add(user.getUserId());

        userService.unfollow(user.getUserId(), follower.getUserId());

        assertFalse(user.getFollowing().contains(follower.getUserId()));
        assertFalse(follower.getFollowers().contains(user.getUserId()));
    }

}