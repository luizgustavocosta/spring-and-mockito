package com.costa.luiz.mockito.post;

import com.costa.luiz.mockito.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PostControllerTest {

    // Creating a post with valid userId and text returns a 200 OK response with the created post
    @Test
    void create_post_with_valid_userid_and_text_returns_200_ok() {
        PostService postService = mock(PostService.class);
        PostController postController = new PostController(postService);
        PostRequest request = new PostRequest("validUserId", "This is a valid post text");
        Post post = new Post("This is a valid post text", new User("validUserId","name"));

        when(postService.create(request.text(), request.userId())).thenReturn(post);

        ResponseEntity<Post> response = postController.create(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(post, response.getBody());
    }

    // Creating a post with an invalid userId throws an IllegalArgumentException
    @Test
    void create_post_with_invalid_userid_throws_illegalargumentexception() {
        PostService postService = mock(PostService.class);
        PostController postController = new PostController(postService);
        PostRequest request = new PostRequest("invalidUserId", "This is a valid post text");

        when(postService.create(request.text(), request.userId())).thenThrow(new IllegalArgumentException());

        assertThrows(IllegalArgumentException.class, () -> {
            postController.create(request);
        });
    }

    // Returns a list of PostResponse objects when posts exist
    @Test
    void test_returns_list_of_postresponse_objects_when_posts_exist() {
        PostService postService = mock(PostService.class);
        PostController postController = new PostController(postService);

        User user = new User("userId", "username");
        Post post = new Post("Sample text", user);
        List<Post> posts = List.of(post);

        when(postService.findAll()).thenReturn(posts);

        List<PostResponse> response = postController.all();

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(post.getId(), response.get(0).id());
        assertEquals(post.getText(), response.get(0).text());
        assertEquals(post.getAuthor(), response.get(0).autor());
        assertEquals(post.getDate(), response.get(0).date());
    }

}