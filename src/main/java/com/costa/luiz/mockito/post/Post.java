package com.costa.luiz.mockito.post;

import com.costa.luiz.mockito.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.ArrayList;

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

    public Post(String texto, User autor) {
        this.texto = texto;
        this.autor = autor;
        this.data = LocalDateTime.now();
    }

    public Post() {
    }

    public void validarTweet(String texto) {
        if (texto.length() > 140) {
            throw new IllegalArgumentException("O tweet não pode ter mais de 140 caracteres");
        }
    }

    public Long getId() {
        return id;
    }
    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public User getAutor() {
        return autor;
    }

    public void setAutor(User autor) {
        this.autor = autor;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }
}
