package com.ebanking.notificationsservice.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SendVerificationCodeResponseTest {

    @Test
    void testBuilder() {
        // Arrange & Act
        SendVerificationCodeResponse response = SendVerificationCodeResponse.builder()
                .code("123456")
                .message("Verification code sent successfully")
                .build();

        // Assert
        assertEquals("123456", response.getCode());
        assertEquals("Verification code sent successfully", response.getMessage());
    }

    @Test
    void testNoArgsConstructor() {
        // Act
        SendVerificationCodeResponse response = new SendVerificationCodeResponse();

        // Assert
        assertNull(response.getCode());
        assertNull(response.getMessage());
    }

    @Test
    void testAllArgsConstructor() {
        // Arrange
        String code = "654321";
        String message = "Code sent to phone number";

        // Act
        SendVerificationCodeResponse response = new SendVerificationCodeResponse(code, message);

        // Assert
        assertEquals(code, response.getCode());
        assertEquals(message, response.getMessage());
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        SendVerificationCodeResponse response1 = SendVerificationCodeResponse.builder()
                .code("123456")
                .message("Success")
                .build();

        SendVerificationCodeResponse response2 = SendVerificationCodeResponse.builder()
                .code("123456")
                .message("Success")
                .build();

        SendVerificationCodeResponse response3 = SendVerificationCodeResponse.builder()
                .code("654321")
                .message("Different message")
                .build();

        // Assert
        assertEquals(response1, response2);
        assertEquals(response1.hashCode(), response2.hashCode());
        assertNotEquals(response1, response3);
        assertNotEquals(response1.hashCode(), response3.hashCode());
    }

    @Test
    void testToString() {
        // Arrange
        SendVerificationCodeResponse response = SendVerificationCodeResponse.builder()
                .code("123456")
                .message("Test message")
                .build();

        // Act
        String toString = response.toString();

        // Assert
        assertTrue(toString.contains("code=123456"));
        assertTrue(toString.contains("message=Test message"));
    }

    @Test
    void testSettersAndGetters() {
        // Arrange
        SendVerificationCodeResponse response = new SendVerificationCodeResponse();

        // Act
        response.setCode("111222");
        response.setMessage("Updated message");

        // Assert
        assertEquals("111222", response.getCode());
        assertEquals("Updated message", response.getMessage());
    }
}
