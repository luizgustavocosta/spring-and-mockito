package com.costa.luiz.mockito.integration;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Value("${rabbitmq.exchange.name}")
    private String topicExchangeName;

    @Value("${rabbitmq.routing.key.new_user}")
    private String routingKeyNewUser;

    @Value("${rabbitmq.routing.key.new_post}")
    private String routingKeyNewPost;
    private final RabbitTemplate rabbitTemplate;

    public NotificationService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void newUser(String message) {
        rabbitTemplate.convertAndSend(topicExchangeName, routingKeyNewUser, message);
    }

    public void newPost(String message) {
        rabbitTemplate.convertAndSend(topicExchangeName, routingKeyNewPost, message);
    }
}
