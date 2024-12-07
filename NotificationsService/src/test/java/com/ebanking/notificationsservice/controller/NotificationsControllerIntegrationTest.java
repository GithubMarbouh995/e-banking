package com.ebanking.notificationsservice.controller;

import com.ebanking.notificationsservice.model.SMS;
import com.ebanking.notificationsservice.model.SendVerificationCodeRequest;
import com.ebanking.notificationsservice.model.SendVerificationCodeResponse;
import com.ebanking.notificationsservice.service.NotificationsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(NotificationsController.class)
class NotificationsControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private NotificationsService notificationsService;

    @Test
    void sendSMS_ValidRequest_Success() throws Exception {
        // Arrange
        SMS sms = SMS.builder()
                .customerFirstName("John")
                .customerLastName("Doe")
                .beneficiaryFirstName("Jane")
                .beneficiaryLastName("Smith")
                .amount(1000.0)
                .phone("+212600000000")
                .pin("1234")
                .ref("REF123")
                .sendRef(true)
                .build();

        when(notificationsService.sendSMS(any(SMS.class)))
                .thenReturn("Message sent successfully to +212600000000");

        // Act & Assert
        mockMvc.perform(post("/api/notifications/send-sms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sms)))
                .andExpect(status().isOk())
                .andExpect(content().string("Message sent successfully to +212600000000"));
    }

    @Test
    void sendSMS_InvalidRequest_ReturnsBadRequest() throws Exception {
        // Arrange
        SMS invalidSms = SMS.builder().build(); // Missing required fields

        // Act & Assert
        mockMvc.perform(post("/api/notifications/send-sms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidSms)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void verifyIdentity_ValidRequest_Success() throws Exception {
        // Arrange
        SendVerificationCodeRequest request = SendVerificationCodeRequest.builder()
                .phone("+212600000000")
                .code("123456")
                .build();

        SendVerificationCodeResponse response = SendVerificationCodeResponse.builder()
                .code("123456")
                .message("Votre code de vérification est : 123456")
                .build();

        when(notificationsService.verifyIdentity(anyString(), anyString()))
                .thenReturn(response);

        // Act & Assert
        mockMvc.perform(post("/api/notifications/OTP")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Votre code de vérification est : 123456"))
                .andExpect(jsonPath("$.code").value("123456"));
    }

    @Test
    void verifyIdentity_InvalidRequest_ReturnsBadRequest() throws Exception {
        // Arrange
        SendVerificationCodeRequest invalidRequest = SendVerificationCodeRequest.builder().build(); // Missing required fields

        // Act & Assert
        mockMvc.perform(post("/api/notifications/OTP")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void verifyIdentity_NullPhone_ReturnsBadRequest() throws Exception {
        // Arrange
        SendVerificationCodeRequest request = SendVerificationCodeRequest.builder()
                .code("123456")
                .build();

        // Act & Assert
        mockMvc.perform(post("/api/notifications/OTP")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void verifyIdentity_NullCode_ReturnsBadRequest() throws Exception {
        // Arrange
        SendVerificationCodeRequest request = SendVerificationCodeRequest.builder()
                .phone("+212600000000")
                .build();

        // Act & Assert
        mockMvc.perform(post("/api/notifications/OTP")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void sendSMS_ServiceThrowsException_ReturnsInternalServerError() throws Exception {
        // Arrange
        SMS sms = SMS.builder()
                .customerFirstName("John")
                .customerLastName("Doe")
                .beneficiaryFirstName("Jane")
                .beneficiaryLastName("Smith")
                .amount(1000.0)
                .phone("+212600000000")
                .pin("1234")
                .ref("REF123")
                .sendRef(true)
                .build();

        when(notificationsService.sendSMS(any(SMS.class)))
                .thenThrow(new RuntimeException("Service error"));

        // Act & Assert
        mockMvc.perform(post("/api/notifications/send-sms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sms)))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Failed to send SMS: Service error"));
    }

    @Test
    void verifyIdentity_ServiceError() throws Exception {
        // Arrange
        SendVerificationCodeRequest request = SendVerificationCodeRequest.builder()
                .phone("+212600000000")
                .code("123456")
                .build();

        when(notificationsService.verifyIdentity(anyString(), anyString()))
                .thenThrow(new RuntimeException("Service error"));

        // Act & Assert
        mockMvc.perform(post("/api/notifications/OTP")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("Failed to verify identity: Service error"));
    }

    @Test
    void sendSMS_InvalidPhoneFormat() throws Exception {
        // Arrange
        SMS sms = SMS.builder()
                .customerFirstName("John")
                .customerLastName("Doe")
                .beneficiaryFirstName("Jane")
                .beneficiaryLastName("Smith")
                .amount(1000.0)
                .phone("1234567890") // Missing + and country code
                .pin("1234")
                .ref("REF123")
                .sendRef(true)
                .build();

        // Act & Assert
        mockMvc.perform(post("/api/notifications/send-sms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sms)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid phone number format"));
    }

    @Test
    void verifyIdentity_InvalidPhoneFormat() throws Exception {
        // Arrange
        SendVerificationCodeRequest request = SendVerificationCodeRequest.builder()
                .phone("1234567890") // Missing + and country code
                .code("123456")
                .build();

        // Act & Assert
        mockMvc.perform(post("/api/notifications/OTP")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid phone number format"));
    }

    @Test
    void verifyIdentity_InvalidCode() throws Exception {
        // Arrange
        SendVerificationCodeRequest request = SendVerificationCodeRequest.builder()
                .phone("+212600000000")
                .code("123") // Too short
                .build();

        // Act & Assert
        mockMvc.perform(post("/api/notifications/OTP")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid verification code"));
    }

    @Test
    void verifyIdentity_WhitespaceInCode_ReturnsBadRequest() throws Exception {
        // Arrange
        SendVerificationCodeRequest request = SendVerificationCodeRequest.builder()
                .phone("+212600000000")
                .code("123 456") // Code with whitespace
                .build();

        // Act & Assert
        mockMvc.perform(post("/api/notifications/OTP")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid verification code"));
    }

    @Test
    void verifyIdentity_EmptyPhone_ReturnsBadRequest() throws Exception {
        // Arrange
        SendVerificationCodeRequest request = SendVerificationCodeRequest.builder()
                .phone("")
                .code("123456")
                .build();

        // Act & Assert
        mockMvc.perform(post("/api/notifications/OTP")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Phone number cannot be empty"));
    }

    @Test
    void verifyIdentity_CodeTooLong_ReturnsBadRequest() throws Exception {
        // Arrange
        SendVerificationCodeRequest request = SendVerificationCodeRequest.builder()
                .phone("+212600000000")
                .code("12345678") // Code longer than 6 digits
                .build();

        // Act & Assert
        mockMvc.perform(post("/api/notifications/OTP")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid verification code"));
    }

    @Test
    void verifyIdentity_SpecialCharactersInPhone_ReturnsBadRequest() throws Exception {
        // Arrange
        SendVerificationCodeRequest request = SendVerificationCodeRequest.builder()
                .phone("+212@600-000-000") // Phone with special characters
                .code("123456")
                .build();

        // Act & Assert
        mockMvc.perform(post("/api/notifications/OTP")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid phone number format"));
    }
}
