package com.costa.luiz.mockito.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/{id}")
    public UserResponse get(@PathVariable("id") Long id) {
        User user = userService.get(id);
        return new UserResponse(user.getId(), user.getUserId(), user.getName(),
                String.join(",", user.getFollowing()),
                String.join(",", user.getFollowers()));
    }

    @PutMapping("/{id}")
    public User update(@PathVariable("id") Long id, User user) {
        return userService.update(id, user);
    }

    @PostMapping("/")
    public User create(UserRequest userRequest) {
        return userService.create(userRequest.toUser());
    }

    @PostMapping("/{id}/follow/{followerId}")
    public User follow(@PathVariable("id") String id, @PathVariable("followerId") String followerId) {
        return userService.follow(id, followerId);
    }

    @GetMapping
    List<UserResponse> all() {
        return userService.all().stream()
                .map(user -> new UserResponse(user.getId(), user.getUserId(), user.getName(),
                        String.join(",", user.getFollowers()),
                        String.join(",", user.getFollowing()))).toList();
    }

}