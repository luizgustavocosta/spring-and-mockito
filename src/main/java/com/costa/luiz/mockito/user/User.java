package com.costa.luiz.mockito.user;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userId;
    private String name;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> followers = new ArrayList<>();
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> following = new ArrayList<>();

    public User() {
    }

    public Long getId() {
        return id;
    }

    public User(String userId, String name) {
        this.userId = userId;
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getFollowers() {
        return followers;
    }

    public void setFollowers(List<String> followers) {
        this.followers = followers;
    }

    public List<String> getFollowing() {
        return following;
    }

    public void setFollowing(List<String> following) {
        this.following = following;
    }

    public static final class UserBuilder {
        private Long id;
        private String userId;
        private String name;
        private List<String> seguidores;
        private List<String> seguindo;

        private UserBuilder() {
        }

        public static UserBuilder anUser() {
            return new UserBuilder();
        }

        public UserBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public UserBuilder withUserId(String userId) {
            this.userId = userId.trim();
            return this;
        }

        public UserBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public UserBuilder withSeguidores(List<String> seguidores) {
            this.seguidores = seguidores;
            return this;
        }

        public UserBuilder withSeguindo(List<String> seguindo) {
            this.seguindo = seguindo;
            return this;
        }

        public User build() {
            User user = new User();
            user.setId(id);
            user.setUserId(userId);
            user.setName(name);
            user.setFollowers(seguidores);
            user.setFollowing(seguindo);
            return user;
        }
    }
}
