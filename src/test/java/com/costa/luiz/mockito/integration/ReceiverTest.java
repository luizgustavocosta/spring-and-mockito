package com.costa.luiz.mockito.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(OutputCaptureExtension.class)
class ReceiverTest {

    Receiver receiver;

    @BeforeEach
    void setUp() {
        receiver = new Receiver();
    }

    @DisplayName("Log message when input is a byte array")
    @Test
    void log_message_when_input_is_byte_array(CapturedOutput capturedOutput) {
        receiver.receiveMessage("Test Message".getBytes());
        assertTrue(capturedOutput.getOut().contains("Received from Exchange Test Message"));

    }

    @DisplayName("Log message when input is a String")
    @Test
    void log_message_when_input_is_string(CapturedOutput capturedOutput) {
        receiver.receiveMessage("Test Message");
        assertTrue(capturedOutput.getOut().contains("Received from Queue < Test Message >"));
    }
}