package com.ebanking.notificationsservice.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SendVerificationCodeRequestTest {

    @Test
    void testBuilder() {
        // Arrange & Act
        SendVerificationCodeRequest request = SendVerificationCodeRequest.builder()
                .phone("+212600000000")
                .code("123456")
                .build();

        // Assert
        assertEquals("+212600000000", request.getPhone());
        assertEquals("123456", request.getCode());
    }

    @Test
    void testNoArgsConstructor() {
        // Act
        SendVerificationCodeRequest request = new SendVerificationCodeRequest();

        // Assert
        assertNull(request.getPhone());
        assertNull(request.getCode());
    }

    @Test
    void testAllArgsConstructor() {
        // Arrange
        String phone = "+212611111111";
        String code = "654321";

        // Act
        SendVerificationCodeRequest request = new SendVerificationCodeRequest(phone, code);

        // Assert
        assertEquals(phone, request.getPhone());
        assertEquals(code, request.getCode());
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        SendVerificationCodeRequest request1 = SendVerificationCodeRequest.builder()
                .phone("+212600000000")
                .code("123456")
                .build();

        SendVerificationCodeRequest request2 = SendVerificationCodeRequest.builder()
                .phone("+212600000000")
                .code("123456")
                .build();

        SendVerificationCodeRequest request3 = SendVerificationCodeRequest.builder()
                .phone("+212611111111")
                .code("654321")
                .build();

        // Assert
        assertEquals(request1, request2);
        assertEquals(request1.hashCode(), request2.hashCode());
        assertNotEquals(request1, request3);
        assertNotEquals(request1.hashCode(), request3.hashCode());
    }

    @Test
    void testToString() {
        // Arrange
        SendVerificationCodeRequest request = SendVerificationCodeRequest.builder()
                .phone("+212600000000")
                .code("123456")
                .build();

        // Act
        String toString = request.toString();

        // Assert
        assertTrue(toString.contains("phone=+212600000000"));
        assertTrue(toString.contains("code=123456"));
    }

    @Test
    void testSettersAndGetters() {
        // Arrange
        SendVerificationCodeRequest request = new SendVerificationCodeRequest();

        // Act
        request.setPhone("+212622222222");
        request.setCode("999888");

        // Assert
        assertEquals("+212622222222", request.getPhone());
        assertEquals("999888", request.getCode());
    }
}
