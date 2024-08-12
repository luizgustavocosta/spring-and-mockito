package com.costa.luiz.mockito;

import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public void postarTweet(String texto, User usuario) {
        postRepository.saveAndFlush(new Post(texto, usuario));
    }

    public List<Post> listarTweets() {
        return postRepository.findAll();
    }

    public Post criarPost(String texto, String usuario) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'criarPost'");
    }
}
