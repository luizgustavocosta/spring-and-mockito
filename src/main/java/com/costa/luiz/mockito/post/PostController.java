package com.costa.luiz.mockito.post;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
class PostController {
    private final PostService postService;

    PostController(PostService postService) {
        this.postService = postService;
    }
    @PostMapping
    ResponseEntity<Post> create(@RequestBody PostRequest request) {
        Post novoPost = postService.create(request.text(), request.userId());
        return ResponseEntity.ok(novoPost);
    }

    @GetMapping("/mock-creation")
    ResponseEntity<Post> mockCreate() {
        PostRequest request = new PostRequest("deadpool","Watch the movie");
        Post novoPost = postService.create(request.text(), request.userId());
        return ResponseEntity.ok(novoPost);
    }

    @GetMapping
    List<PostResponse> all() {
        return postService.findAll().stream()
                .map(post-> new PostResponse(post.getId(), post.getText(), post.getAuthor(), post.getDate()))
                .toList();
    }
}

