package com.costa.luiz.mockito;

public class UserController {
    
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Adicione métodos do controlador aqui
}