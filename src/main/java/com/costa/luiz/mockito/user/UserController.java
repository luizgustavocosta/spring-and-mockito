package com.costa.luiz.mockito.user;

import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/users")
class UserController {

    private final UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    UserResponse get(@PathVariable("id") Long id) {
        var user = userService.get(id);
        return toUserResponse(user);
    }

    @PostMapping
    UserResponse create(UserRequest userRequest) {
        var user = userService.create(userRequest.toUser());
        return toUserResponse(user);
    }

    @PostMapping("/{id}/follow/{followerId}")
    User follow(@PathVariable("id") String id, @PathVariable("followerId") String followerId) {
        return userService.follow(id, followerId);
    }

    @PostMapping("/{id}/unfollow/{followerId}")
    UserResponse unfollow(@PathVariable("id") String id, @PathVariable("followerId") String followerId) {
        return toUserResponse(userService.unfollow(id, followerId));
    }

    @GetMapping
    public List<UserResponse> all() {
        return userService.all().stream()
                .map(this::toUserResponse).toList();
    }

    private UserResponse toUserResponse(User user) {
        return new UserResponse(user.getId(), user.getUserId(), user.getName(),
                listAsString(user.getFollowers()),
                listAsString(user.getFollowing()));
    }
    private String listAsString(List<String> elements) {
        if (Objects.nonNull(elements)) {
            return String.join(",", elements);
        }
        return "";
    }
}