package com.costa.luiz.mockito;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/post")
class PostController {
    private final PostService postService;

    PostController(PostService postService) {
        this.postService = postService;
    }
    @PostMapping
    ResponseEntity<Post> postarMensagem(@RequestBody PostRequest request) {
        Post novoPost = postService.criarPost(request.getTexto(), request.getUsuario());
        return ResponseEntity.ok(novoPost);
    }
}

class PostRequest {
    private String texto;
    private String usuario;

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}
