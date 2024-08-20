package com.costa.luiz.mockito.integration;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(OutputCaptureExtension.class)
class QueueListenerTest {
    @Test
    // Listener receives a valid message from the queue
    void test_listener_receives_valid_message(CapturedOutput capturedOutput) {
        QueueListener listener = new QueueListener();
        String message = "Valid message";
        listener.listener(message);
        assertTrue(capturedOutput.getOut().contains("Message received from queue: Valid message"));
    }

    // Listener method handles empty string messages
    // Listener receives an empty message
    @Test
    void test_listener_receives_empty_message(CapturedOutput capturedOutput) {
        QueueListener listener = new QueueListener();
        String message = "";
        listener.listener(message);
        assertTrue(capturedOutput.getOut().contains("Message received from queue"));
    }
}