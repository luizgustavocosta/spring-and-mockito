package com.costa.luiz.mockito.integration;

import org.junit.jupiter.api.BeforeEach;
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

    // Sending a new user message to the correct exchange and routing key
    @Test
    void test_send_new_user_message() {
        ReflectionTestUtils.setField(notificationService, "topicExchangeName", "exchange.sboot-mockito");
        ReflectionTestUtils.setField(notificationService, "routingKeyNewUser", "mockito.new_user");

        String message = "Welcome new user!";
        notificationService.newUser(message);

        verify(rabbitTemplate).convertAndSend("exchange.sboot-mockito", "mockito.new_user", message);
    }

    // Handling null messages when sending a new user message
    @Test
    void test_handle_null_message_for_new_user() {
        ReflectionTestUtils.setField(notificationService, "topicExchangeName", "exchange.sboot-mockito");
        ReflectionTestUtils.setField(notificationService, "routingKeyNewPost", "mockito.new_post");

        notificationService.newPost("Hello World");

        verify(rabbitTemplate).convertAndSend("exchange.sboot-mockito", "mockito.new_post", "Hello World");
    }


}