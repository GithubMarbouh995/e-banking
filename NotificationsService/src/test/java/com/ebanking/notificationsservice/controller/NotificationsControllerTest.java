package com.ebanking.notificationsservice.controller;

import com.ebanking.notificationsservice.model.Customer;
import com.ebanking.notificationsservice.model.SMS;
import com.ebanking.notificationsservice.model.SendVerificationCodeRequest;
import com.ebanking.notificationsservice.model.SendVerificationCodeResponse;
import com.ebanking.notificationsservice.service.NotificationsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationsControllerTest {

    @Mock
    private NotificationsService notificationsService;

    @InjectMocks
    private NotificationsController notificationsController;

    @Test
    void sendSMS_Success() {
        // Given
        SMS sms = SMS.builder()
                .phone("+212600000000")
                .customerFirstName("John")
                .customerLastName("Doe")
                .beneficiaryFirstName("Jane")
                .beneficiaryLastName("Smith")
                .amount(1000.0)
                .pin("1234")
                .ref("REF123")
                .sendRef(true)
                .build();

        when(notificationsService.sendSMS(any(SMS.class)))
                .thenReturn("Message sent successfully");

        // When
        ResponseEntity<String> response = notificationsController.sendSMS(sms);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Message sent successfully", response.getBody());
        verify(notificationsService).sendSMS(sms);
    }

    @Test
    void sendSMS_NullSMS() {
        // When
        ResponseEntity<String> response = notificationsController.sendSMS(null);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid SMS request", response.getBody());
        verify(notificationsService, never()).sendSMS(any(SMS.class));
    }

    @Test
    void sendSMS_InvalidPhoneNumber() {
        // Given
        SMS sms = SMS.builder()
                .phone("invalid-phone")
                .customerFirstName("John")
                .customerLastName("Doe")
                .build();

        // When
        ResponseEntity<String> response = notificationsController.sendSMS(sms);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid phone number format", response.getBody());
        verify(notificationsService, never()).sendSMS(any(SMS.class));
    }

    @Test
    void verifyIdentity_Success() {
        // Given
        SendVerificationCodeRequest request = SendVerificationCodeRequest.builder()
                .phone("+212600000000")
                .code("123456")
                .build();

        SendVerificationCodeResponse expectedResponse = new SendVerificationCodeResponse("123456", "Verification successful");
        when(notificationsService.verifyIdentity(anyString(), anyString()))
                .thenReturn(expectedResponse);

        // When
        ResponseEntity<SendVerificationCodeResponse> response = notificationsController.verifyIdentity(request);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("123456", response.getBody().getCode());
        assertEquals("Verification successful", response.getBody().getMessage());
        verify(notificationsService).verifyIdentity(request.getPhone(), request.getCode());
    }

    @Test
    void verifyIdentity_NullRequest() {
        // When
        ResponseEntity<SendVerificationCodeResponse> response = notificationsController.verifyIdentity(null);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Invalid verification request", response.getBody().getMessage());
        verify(notificationsService, never()).verifyIdentity(anyString(), anyString());
    }

    @Test
    void verifyIdentity_InvalidPhoneNumber() {
        // Given
        SendVerificationCodeRequest request = SendVerificationCodeRequest.builder()
                .phone("invalid-phone")
                .code("123456")
                .build();

        // When
        ResponseEntity<SendVerificationCodeResponse> response = notificationsController.verifyIdentity(request);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Invalid phone number format", response.getBody().getMessage());
        verify(notificationsService, never()).verifyIdentity(anyString(), anyString());
    }

    @Test
    void verifyIdentity_InvalidCode() {
        // Given
        SendVerificationCodeRequest request = SendVerificationCodeRequest.builder()
                .phone("+212600000000")
                .code("123")
                .build();

        // When
        ResponseEntity<SendVerificationCodeResponse> response = notificationsController.verifyIdentity(request);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Invalid verification code", response.getBody().getMessage());
        verify(notificationsService, never()).verifyIdentity(anyString(), anyString());
    }

    @Test
    void test_Success() {
        // When
        ResponseEntity<String> response = notificationsController.test();

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Notifications service is running", response.getBody());
    }

    @Test
    void testCustomer_Success() throws JsonProcessingException {
        // Given
        Customer customer = new Customer();
        when(notificationsService.test(any(Customer.class)))
                .thenReturn("Customer processed successfully");

        // When
        ResponseEntity<String> response = notificationsController.test(customer);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Customer processed successfully", response.getBody());
        verify(notificationsService).test(customer);
    }

    @Test
    void testCustomer_Error() throws JsonProcessingException {
        // Given
        Customer customer = new Customer();
        when(notificationsService.test(any(Customer.class)))
                .thenThrow(new JsonProcessingException("Invalid customer data") {});

        // When
        ResponseEntity<String> response = notificationsController.test(customer);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().contains("Failed to process customer"));
        verify(notificationsService).test(customer);
    }
}