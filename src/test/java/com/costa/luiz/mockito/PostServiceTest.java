package com.costa.luiz.mockito;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PostServiceTest {

    @Test
    void test_create_post_success() {
        // Arrange
        PostRepository postRepository = Mockito.mock(PostRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        PostService postService = new PostService(postRepository, userRepository);
        User user = new User();
        String text = "Valid tweet text";
        String userId = "validUserId";

        Mockito.when(userRepository.findUsersByUserId(userId)).thenReturn(Optional.of(user));
        Mockito.when(postRepository.save(Mockito.any(Post.class))).thenAnswer(i -> i.getArguments()[0]);

        // Act
        Post result = postService.create(text, userId);

        // Assert
        assertNotNull(result);
        assertEquals(text, result.getTexto());
        assertEquals(user, result.getAutor());
    }

    @Test
    void test_create_post_user_not_found() {
        // Arrange
        PostRepository postRepository = Mockito.mock(PostRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        PostService postService = new PostService(postRepository, userRepository);
        String text = "Valid tweet text";
        String userId = "invalidUserId";

        Mockito.when(userRepository.findUsersByUserId(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            postService.create(text, userId);
        });
    }
}