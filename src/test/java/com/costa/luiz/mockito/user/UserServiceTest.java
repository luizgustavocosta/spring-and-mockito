package com.costa.luiz.mockito.user;

import com.costa.luiz.mockito.integration.NotificationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock UserRepository userRepository;
    @Mock NotificationService notificationService;

    @DisplayName("Successfully creates a new user and returns the user object")
    @Test
    void create_user_success() {
        var newUser = new User("user123", "John Doe");
        var savedUser = new User("user123", "John Doe");
        savedUser.setId(1L);

        Mockito.when(userRepository.save(newUser)).thenReturn(savedUser);

        var result = userService.create(newUser);

        Assertions.assertNotNull(result);
        assertEquals("user123", result.getUserId());
        assertEquals("John Doe", result.getName());
        Mockito.verify(notificationService).newUser("User user123 has been created");
    }

    @DisplayName("Attempt to create a user with null values")
    @Test
    void attempt_to_create_user_with_null_values() {
        User newUser = new User(null, null);

        when(userRepository.save(newUser)).thenThrow(new IllegalArgumentException("User details cannot be null"));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.create(newUser);
        });

        assertEquals("User details cannot be null", exception.getMessage());
        verify(notificationService, never()).newUser(anyString());
    }

    @DisplayName("User successfully follows another user")
    @Test
    void user_successfully_follows_another_user() {
        var user = new User("user1", "User One");
        var userToFollow = new User("user2", "User Two");

        when(userRepository.findUsersByUserId("user1")).thenReturn(Optional.of(user));
        when(userRepository.findUsersByUserId("user2")).thenReturn(Optional.of(userToFollow));

        var result = userService.follow("user1", "user2");

        Assertions.assertTrue(result.getFollowing().contains("user2"));
        Assertions.assertTrue(userToFollow.getFollowers().contains("user1"));
        Mockito.verify(userRepository).saveAll(List.of(user, userToFollow));
    }

    @DisplayName("User successfully retrieves an existing user by id")
    @Test
    void retrieve_existing_user_by_id_successfully() {
        var mockUser = new User("user123", "John Doe");
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(mockUser));

        var result = userService.get(1L);

        assertEquals("user123", result.getUserId());
        assertEquals("John Doe", result.getName());
    }

    @DisplayName("User throws an exception when user id is not found")
    @Test
    void throw_illegal_state_exception_when_user_id_not_found() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(IllegalStateException.class, () -> userService.get(1L));
    }

    @DisplayName("User not found in the repository")
    @Test
    void user_not_found_in_repository() {
        when(userRepository.findUsersByUserId("user1")).thenReturn(Optional.empty());

        var exception = assertThrows(IllegalStateException.class, () -> {
            userService.follow("user1", "user2");
        });

        assertEquals("User not found", exception.getMessage());
    }

    @DisplayName("User tries to unfollow a non-existent user")
    @Test
    void user_tries_to_unfollow_non_existent_user() {
        var user = new User("user1", "User One");
        when(userRepository.findUsersByUserId("user1")).thenReturn(Optional.of(user));

        userService.unfollow(user.getUserId(), "nonExistentUser");

        assertFalse(user.getFollowing().contains("nonExistentUser"));
    }

    @DisplayName("User successfully unfollows another user")
    @Test
    void user_successfully_unfollows_another_user() {
        var user = new User("user1", "User One");
        var follower = new User("user2", "User Two");
        when(userRepository.findUsersByUserId(anyString()))
                .thenReturn(Optional.of(user))
                .thenReturn(Optional.of(follower));

        user.getFollowing().add(follower.getUserId());
        follower.getFollowers().add(user.getUserId());

        userService.unfollow(user.getUserId(), follower.getUserId());

        verify(userRepository, times(2)).save(any(User.class));
        assertFalse(user.getFollowing().contains(follower.getUserId()));
        assertFalse(follower.getFollowers().contains(user.getUserId()));
    }

}