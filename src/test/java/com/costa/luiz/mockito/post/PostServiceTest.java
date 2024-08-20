package com.costa.luiz.mockito.post;

import com.costa.luiz.mockito.integration.NotificationService;
import com.costa.luiz.mockito.user.User;
import com.costa.luiz.mockito.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock PostRepository postRepository;
    @Mock UserRepository userRepository;
    @Mock
    NotificationService notificationService;
    PostService postService;
    @BeforeEach
    void setUp() {
        postService = new PostService(postRepository, userRepository, notificationService);
    }

    @DisplayName("Returns a list of posts when posts are available in the repository")
    @Test
    void returns_list_of_posts_when_available() {
        var expectedPosts = List.of(new Post("Post 1", new User()),
                new Post("Post 2", new User()));

        when(postRepository.findAll()).thenReturn(expectedPosts);

        assertEquals(expectedPosts, postService.findAll());
    }

    @DisplayName("Handles the scenario where the postRepository returns null")
    @Test
    void handles_null_return_from_postRepository() {
        when(postRepository.findAll()).thenReturn(null);

        assertNull(postService.findAll());
    }

    @DisplayName("Successfully create a post when valid text and userId are provided")
    @Test
    void create_post_success() {
        // Arrange
        String text = "Valid post text";
        String userId = "validUserId";
        var user = new User();
        var post = new Post(text, user);

        when(userRepository.findUsersByUserId(userId)).thenReturn(Optional.of(user));
        when(postRepository.save(any(Post.class))).thenReturn(post);
        var argumentText = ArgumentCaptor.forClass(String.class);

        // Act
        var createdPost = postService.create(text, userId);

        // Assert

        verify(notificationService).newPost(argumentText.capture());
        assertEquals(argumentText.getValue(), text);

        assertNotNull(createdPost);
        assertEquals(text, createdPost.getText());
        assertEquals(user, createdPost.getAuthor());
    }

    @DisplayName("Throws an IllegalArgumentException when userId does not exist in userRepository")
    @Test
    void create_post_user_not_found() {
        // Arrange
        var text = "Valid post text";
        var userId = "invalidUserId";

        when(userRepository.findUsersByUserId(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> postService.create(text, userId));
    }

}