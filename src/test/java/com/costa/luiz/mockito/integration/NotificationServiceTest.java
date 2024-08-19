package com.costa.luiz.mockito.integration;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class NotificationServiceTest {

    // Sending a new user message to the correct exchange and routing key
    @Test
    void test_send_new_user_message() {
        RabbitTemplate rabbitTemplate = mock(RabbitTemplate.class);
        NotificationService notificationService = new NotificationService(rabbitTemplate);
        String message = "Welcome new user!";

        notificationService.newUser(message);

        verify(rabbitTemplate).convertAndSend("exchange.sboot-mockito", "mockito.new_user", message);
    }

    // Handling null messages when sending a new user message
    @Test
    public void test_handle_null_message_for_new_user() {
        RabbitTemplate rabbitTemplate = mock(RabbitTemplate.class);
        NotificationService notificationService = new NotificationService(rabbitTemplate);

        notificationService.newPost("Hello World");

        verify(rabbitTemplate).convertAndSend("exchange.sboot-mockito", "mockito.new_post", "Hello World");
    }

}