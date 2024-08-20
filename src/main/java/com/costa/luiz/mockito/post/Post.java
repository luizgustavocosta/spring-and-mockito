package com.costa.luiz.mockito.post;

import com.costa.luiz.mockito.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String text;
    @OneToOne
    private User author;
    private LocalDateTime date;

    public Post(String text, User author) {
        this.text = text;
        this.author = author;
        this.date = LocalDateTime.now();
    }

    public Post() {
    }

    public Long getId() {
        return id;
    }
    public String getText() {
        return text;
    }

    public User getAuthor() {
        return author;
    }

    public LocalDateTime getDate() {
        return date;
    }
}
