package com.costa.luiz.mockito.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(OutputCaptureExtension.class)
class QueueListenerTest {

    QueueListener listener;

    @BeforeEach
    void setUp() {
        listener = new QueueListener();
    }

    @DisplayName("Listener receives a valid message from the queue")
    @Test
    void listener_receives_valid_message(CapturedOutput capturedOutput) {
        var message = "Valid message";
        listener.listener(message);
        assertTrue(capturedOutput.getOut().contains("Message received from queue: " + message));
    }

    @DisplayName("Listener receives an empty message from the queue")
    @Test
    void listener_receives_empty_message(CapturedOutput capturedOutput) {
        var message = "";
        listener.listener(message);
        assertTrue(capturedOutput.getOut().contains("Message received from queue" + message));
    }
}