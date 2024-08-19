package com.costa.luiz.mockito.integration;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    static final String topicExchangeName = "exchange.sboot-mockito";

    private final RabbitTemplate rabbitTemplate;

    public NotificationService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void newUser(String message) {
        rabbitTemplate.convertAndSend(topicExchangeName, "mockito.new_user", message);
    }
    public void newPost(String message) {
        rabbitTemplate.convertAndSend(topicExchangeName, "mockito.new_post", message);
    }
}
