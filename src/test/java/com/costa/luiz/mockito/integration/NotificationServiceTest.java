package com.costa.luiz.mockito.integration;

import com.costa.luiz.mockito.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

    @Mock
    RabbitTemplate rabbitTemplate;
    NotificationService notificationService;

    @BeforeEach
    void setUp() {
        notificationService = new NotificationService(rabbitTemplate);
    }

    @DisplayName("Sending a new user message to the correct exchange and routing key")
    @Test
    void send_new_user_message() {
        ReflectionTestUtils.setField(notificationService, "topicExchangeName", "exchange.sboot-mockito");
        ReflectionTestUtils.setField(notificationService, "routingKeyNewUser", "mockito.new_user");

        var message = "Welcome new user!";
        notificationService.newUser(message);

        verify(rabbitTemplate).convertAndSend("exchange.sboot-mockito", "mockito.new_user", message);
    }

    @DisplayName("Handling null messages when sending a new user message")
    @Test
    void handle_null_message_for_new_user() {
        ReflectionTestUtils.setField(notificationService, "topicExchangeName", "exchange.sboot-mockito");
        ReflectionTestUtils.setField(notificationService, "routingKeyNewPost", "mockito.new_post");

        var message = "Hello World";
        notificationService.newPost("Hello World");

        verify(rabbitTemplate)
                .convertAndSend("exchange.sboot-mockito", "mockito.new_post", message);
    }

    @DisplayName("Sending a valid User object results in a message being sent to the correct exchange and routing key")
    @Test
    void valid_user_message_sent() {
        // Arrange
        ReflectionTestUtils.setField(notificationService, "topicExchangeName", "exchange.sboot-mockito");
        ReflectionTestUtils.setField(notificationService, "routingKeyNewUser", "mockito.new_user");
        var user = new User("user123", "John Doe");

        // Act
        notificationService.newUser(user);

        // Assert
        verify(rabbitTemplate).convertAndSend("exchange.sboot-mockito", "mockito.new_user", user);
    }

}