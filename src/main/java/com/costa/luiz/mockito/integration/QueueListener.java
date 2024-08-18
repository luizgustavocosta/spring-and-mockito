package com.costa.luiz.mockito.integration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
class QueueListener {

    private static final Logger log = LoggerFactory.getLogger(QueueListener.class);

    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void listener(String message) {
        log.info("Message received from queue: {}", message);
    }

    @RabbitListener(id = "consumer", queues = "stream.sboot-mockito")
    public void consumerStream(Message message) {
        log.info("Message received from stream: {}", new String(message.getBody()));
    }

}
