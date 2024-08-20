package com.costa.luiz.mockito.post;

import com.costa.luiz.mockito.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PostControllerTest {

    @DisplayName("Create a post with valid userId and text returns 200 OK")
    @Test
    void create_post_with_valid_userid_and_text_returns_200_ok() {
        var postService = mock(PostService.class);
        var postController = new PostController(postService);
        var request = new PostRequest("validUserId", "This is a valid post text");
        var post = new Post("This is a valid post text", new User("validUserId","name"));

        when(postService.create(request.text(), request.userId())).thenReturn(post);

        ResponseEntity<Post> response = postController.create(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(post, response.getBody());
    }

    @DisplayName("Create a post with invalid userId throws an IllegalArgumentException")
    @Test
    void create_post_with_invalid_userid_throws_illegalargumentexception() {
        var postService = mock(PostService.class);
        var postController = new PostController(postService);
        var request = new PostRequest("invalidUserId", "This is a valid post text");

        when(postService.create(request.text(), request.userId())).thenThrow(new IllegalArgumentException());

        assertThrows(IllegalArgumentException.class, () -> {
            postController.create(request);
        });
    }

    @DisplayName("Returns a list of PostResponse objects when posts exist")
    @Test
    void returns_list_of_postresponse_objects_when_posts_exist() {
        var postService = mock(PostService.class);
        var postController = new PostController(postService);

        var user = new User("userId", "username");
        var post = new Post("Sample text", user);
        var posts = List.of(post);

        when(postService.findAll()).thenReturn(posts);

        var response = postController.all();

        assertThat(response.iterator().next())
                .extracting("id", "text", "autor", "date")
                .containsExactly(post.getId(), post.getText(), post.getAuthor(), post.getDate());
    }

}