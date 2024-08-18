//package com.costa.luiz.mockito.omni;
//
//import com.costa.luiz.mockito.user.User;
//import com.costa.luiz.mockito.user.UserRepository;
//import com.costa.luiz.mockito.user.UserService;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//
//import java.util.ArrayList;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//class UserServiceTest {
//
//    // Retrieves user when ID exists in repository
//    @Test
//    void test_retrieves_user_when_id_exists() {
//        UserRepository userRepository = Mockito.mock(UserRepository.class);
//        UserService userService = new UserService(userRepository);
//        User expectedUser = new User("user123", "John Doe");
//        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(expectedUser));
//
//        User actualUser = userService.get(1L);
//
//        Assertions.assertNotNull(actualUser);
//        assertEquals(expectedUser.getName(), actualUser.getName());
//    }
//
//    //Edge case
//    // Handles null ID input gracefully
//    @Test
//    void test_handles_null_id_input() {
//        UserRepository userRepository = Mockito.mock(UserRepository.class);
//        UserService userService = new UserService(userRepository);
//
//        User actualUser = userService.get(null);
//
//        Assertions.assertNull(actualUser);
//    }
//
//    // Update existing user when user ID is found
//    @Test
//    void test_update_existing_user() {
//        UserRepository userRepository = Mockito.mock(UserRepository.class);
//        UserService userService = new UserService(userRepository);
//        User existingUser = new User("user123", "John Doe");
//        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
//
//        User updatedUser = new User("user123", "Jane Doe");
//        User result = userService.update(1L, updatedUser);
//
//        Mockito.verify(userRepository).save(updatedUser);
//        assertEquals("Jane Doe", result.getName());
//    }
//
//    // Edge case Handle case when user ID is not found
//    @Test
//    void test_update_user_not_found() {
//        UserRepository userRepository = Mockito.mock(UserRepository.class);
//        UserService userService = new UserService(userRepository);
//        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.empty());
//
//        User updatedUser = new User("user123", "Jane Doe");
//
//        Exception exception = assertThrows(IllegalStateException.class, () -> {
//            userService.update(1L, updatedUser);
//        });
//
//        assertEquals("User not found", exception.getMessage());
//    }
//
//    // User successfully follows another user
//    @Test
//    public void test_user_successfully_follows_another_user() {
//        UserRepository userRepository = Mockito.mock(UserRepository.class);
//        UserService userService = new UserService(userRepository);
//
//        User user = new User("user1", "User One");
//        user.setId(1L);
//        user.setSeguindo(new ArrayList<>());
//        user.setSeguidores(new ArrayList<>());
//
//        User targetUser = new User("user2", "User Two");
//        targetUser.setId(2L);
//        targetUser.setSeguindo(new ArrayList<>());
//        targetUser.setSeguidores(new ArrayList<>());
//
//        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
//        String fix = "1";
//        userService.follow(fix, fix);
//
//        assertTrue(user.getSeguindo().contains("user2"));
//        assertTrue(user.getSeguidores().contains("user2"));
//    }
//
//    // User does not exist in the repository
//    @Test
//    public void test_user_does_not_exist_in_repository() {
//        UserRepository userRepository = Mockito.mock(UserRepository.class);
//        UserService userService = new UserService(userRepository);
//
//        User targetUser = new User("user2", "User Two");
//        targetUser.setId(2L);
//
//        Mockito.when(userRepository.findById(2L)).thenReturn(Optional.empty());
//
//        Exception exception = assertThrows(IllegalStateException.class, () -> {
//            userService.follow(targetUser);
//        });
//
//        assertEquals("User not found", exception.getMessage());
//    }
//}