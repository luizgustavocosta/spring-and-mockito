package com.costa.luiz.mockito.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @InjectMocks
    UserController userController;

    @Mock
    UserService userService;
    
    @DisplayName("Retrieve a user by ID successfully")
    @Test
    void retrieve_user_by_id_successfully() {
        var user = new User("user123", "John Doe");
        user.setId(1L);
        when(userService.get(1L)).thenReturn(user);

        UserResponse response = userController.get(1L);

        assertNotNull(response);
        assertEquals(1L, response.id());
        assertEquals("user123", response.userId());
        assertEquals("John Doe", response.name());
    }

    @DisplayName("Retrieve a user that does not exist")
    @Test
    void retrieve_nonexistent_user() {
        when(userService.get(999L)).thenThrow(IllegalArgumentException.class);
        assertThrows(IllegalArgumentException.class, () -> userController.get(999L));
    }

    @DisplayName("Follow another user successfully")
    @Test
    void follow_another_user_successfully() {
        User user = new User("user123", "John Doe");
        user.setId(1L);
        when(userService.follow("1", "2")).thenReturn(user);

        User response = userController.follow("1", "2");

        assertNotNull(response);
        assertEquals("user123", response.getUserId());
        assertEquals("John Doe", response.getName());
    }

    @DisplayName("Follow oneself")
    @Test
    void follow_oneself() {
        User user = new User("user123", "John Doe");
        user.setId(1L);
        when(userService.follow("user123", "user123")).thenThrow(IllegalArgumentException.class);

        assertThrows(IllegalArgumentException.class, () -> userController.follow("user123", "user123"));
    }
}