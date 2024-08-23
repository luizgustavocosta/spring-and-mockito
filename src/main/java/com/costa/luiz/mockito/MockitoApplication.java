package com.costa.luiz.mockito;

import com.costa.luiz.mockito.user.User;
import com.costa.luiz.mockito.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.util.List;

@SpringBootApplication
public class MockitoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MockitoApplication.class, args);
    }

    @Autowired
    private UserRepository userRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void start() {
        var deadpool = new User("deadpool", "Wade Wilson");
        var wolverine = new User("wolverine", "James Howlett");
        var gambit = new User("gambit", "Remy Etienne Lebau");
        var storm = new User("storm", "Ororo Munroe");
        var xavier = new User("xavier", "Charles Francis Xavier");
        var magneto = new User("magneto", "Max Eisenhardt");
        userRepository.saveAll(List.of(deadpool, wolverine, gambit, storm, xavier, magneto));
    }

}
