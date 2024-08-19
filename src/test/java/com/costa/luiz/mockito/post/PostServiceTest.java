package com.costa.luiz.mockito.post;

import com.costa.luiz.mockito.integration.NotificationService;
import com.costa.luiz.mockito.user.User;
import com.costa.luiz.mockito.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    // findById throws IllegalArgumentException when an invalid ID is provided

    @Mock PostRepository postRepository;
    @Mock UserRepository userRepository;
    @Mock
    NotificationService notificationService;
    PostService postService;
    @BeforeEach
    void setUp() {
        postService = new PostService(postRepository, userRepository, notificationService);
    }

    // Returns a list of posts when posts are available in the repository
    @Test
    public void test_returns_list_of_posts_when_available() {

        List<Post> expectedPosts = List.of(new Post("Post 1", new User()),
                new Post("Post 2", new User()));
        when(postRepository.findAll()).thenReturn(expectedPosts);

        List<Post> actualPosts = postService.findAll();

        assertEquals(expectedPosts, actualPosts);
    }

    // Handles the scenario where the postRepository returns null
    @Test
    public void test_handles_null_return_from_postRepository() {
        PostRepository postRepository = mock(PostRepository.class);
        UserRepository userRepository = mock(UserRepository.class);
        NotificationService notificationService = mock(NotificationService.class);
        PostService postService = new PostService(postRepository, userRepository, notificationService);

        when(postRepository.findAll()).thenReturn(null);

        List<Post> actualPosts = postService.findAll();

        assertNull(actualPosts);
    }

    // Successfully create a post when valid text and userId are provided
    @Test
    public void test_create_post_success() {
        // Arrange
        String text = "Valid post text";
        String userId = "validUserId";
        User user = new User();
        Post post = new Post(text, user);

        when(userRepository.findUsersByUserId(userId)).thenReturn(Optional.of(user));
        when(postRepository.save(any(Post.class))).thenReturn(post);

        // Act
        Post createdPost = postService.create(text, userId);

        // Assert
        assertNotNull(createdPost);
        assertEquals(text, createdPost.getText());
        assertEquals(user, createdPost.getAuthor());
        verify(notificationService).newPost(text);
    }

    // Throw IllegalArgumentException when userId does not exist in userRepository
    @Test
    public void test_create_post_user_not_found() {
        // Arrange
        String text = "Valid post text";
        String userId = "invalidUserId";

        when(userRepository.findUsersByUserId(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> postService.create(text, userId));
    }

}