package com.costa.luiz.mockito;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String texto;
    @OneToOne
    private User autor;
    private LocalDateTime data;
    @ManyToMany
    private List<User> likes;

    public Post(String texto, User autor) {
        this.texto = texto;
        this.autor = autor;
        this.data = LocalDateTime.now();
        this.likes = new ArrayList<>();
    }

    public Post() {
    }

    public void validarTweet(String texto) {
        if (texto.length() > 140) {
            throw new IllegalArgumentException("O tweet não pode ter mais de 140 caracteres");
        }
    }

    public void adicionarLike(User usuario) {
        if (!likes.contains(usuario.getName())) {
            likes.add(usuario);
            System.out.println(usuario.getName() + " curtiu o tweet de " + autor.getName());
        } else {
            System.out.println(usuario.getName() + " já curtiu este tweet anteriormente");
        }
    }

    public String getTexto() {
        return texto;
    }

    public User getAutor() {
        return autor;
    }
}
