package com.costa.luiz.mockito;

import com.costa.luiz.mockito.user.User;
import com.costa.luiz.mockito.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class MockitoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MockitoApplication.class, args);
    }

    @Autowired
    private UserRepository userRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void start() {
        User deadpool = new User("deadpool", "Wade Wilson");
        userRepository.save(deadpool);

        User wolverine = new User("wolverine", "James Howlett");
        userRepository.save(wolverine);
    }

}
