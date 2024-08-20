package com.costa.luiz.mockito.integration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
class Receiver {
    private static final Logger log = LoggerFactory.getLogger(Receiver.class);

    void receiveMessage(Object message) {
        if (message instanceof String) {
            log.info("Received from Queue < {} >", message);
        } else if (message instanceof byte[] messageAsByteArray) {
            log.info("Received from Exchange {}", new String(messageAsByteArray));
        }
    }
}
