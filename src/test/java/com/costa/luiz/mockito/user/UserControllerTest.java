package com.costa.luiz.mockito.user;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserControllerTest {
    // Retrieve a user by ID successfully
    @Test
    void test_retrieve_user_by_id_successfully() {
        UserService userService = mock(UserService.class);
        UserController userController = new UserController(userService);
        User user = new User("user123", "John Doe");
        user.setId(1L);
        when(userService.get(1L)).thenReturn(user);

        UserResponse response = userController.get(1L);

        assertNotNull(response);
        assertEquals(1L, response.id());
        assertEquals("user123", response.userId());
        assertEquals("John Doe", response.name());
    }

    // Retrieve a user that does not exist
    @Test
    void test_retrieve_nonexistent_user() {
        UserService userService = mock(UserService.class);
        UserController userController = new UserController(userService);
        when(userService.get(999L)).thenThrow(IllegalArgumentException.class);

        assertThrows(IllegalArgumentException.class, () -> userController.get(999L));
    }

    // Follow another user successfully
    @Test
    void test_follow_another_user_successfully() {
        UserService userService = mock(UserService.class);
        UserController userController = new UserController(userService);
        User user = new User("user123", "John Doe");
        user.setId(1L);
        when(userService.follow("1", "2")).thenReturn(user);

        User response = userController.follow("1", "2");

        assertNotNull(response);
        assertEquals("user123", response.getUserId());
        assertEquals("John Doe", response.getName());
    }

    // Follow oneself
    @Test
    void test_follow_oneself() {
        UserService userService = mock(UserService.class);
        UserController userController = new UserController(userService);
        User user = new User("user123", "John Doe");
        user.setId(1L);
        when(userService.follow("user123", "user123")).thenThrow(IllegalArgumentException.class);

        assertThrows(IllegalArgumentException.class, () -> userController.follow("user123", "user123"));
    }
}