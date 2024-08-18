package com.costa.luiz.mockito.user;

public record UserRequest(Long id, String userId, String name) {
    public User toUser() {
        return User.UserBuilder.anUser()
                .withId(id)
                .withUserId(userId)
                .withName(name)
                .build();
    }
}
