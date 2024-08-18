package com.costa.luiz.mockito.integration;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RabbitService {

    static final String topicExchangeName = "exchange.sboot-mockito";

    static final String queueName = "rabbitmq.queue.name";

    private final RabbitTemplate rabbitTemplate;

    public RabbitService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(String message) {
        rabbitTemplate.convertAndSend(topicExchangeName, "mockito.welcome_kit", "Hello from RabbitMQ! at " + LocalDateTime.now());
    }
}
