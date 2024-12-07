package com.ebanking.notificationsservice.service;

import com.ebanking.notificationsservice.model.SMS;
import com.ebanking.notificationsservice.model.SendVerificationCodeResponse;
import com.vonage.client.VonageClient;
import com.vonage.client.sms.*;
import com.vonage.client.sms.messages.TextMessage;
import com.vonage.client.verify.CheckResponse;
import com.vonage.client.verify.VerifyClient;
import com.vonage.client.verify.VerifyStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationsServiceImplTest {

    @Mock
    private Environment environment;

    private NotificationsServiceImpl notificationsService;

    @BeforeEach
    void setUp() {
        notificationsService = new NotificationsServiceImpl("test-key", "test-secret", environment);
    }

    @Test
    void sendSMS_Success() throws Exception {
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

        // When
        when(environment.getActiveProfiles()).thenReturn(new String[]{"test"});
        String result = notificationsService.sendSMS(sms);

        // Then
        assertTrue(result.contains("successfully"));
    }

    @Test
    void sendSMS_Error() throws Exception {
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

        // Configure environment to not be test
        when(environment.getActiveProfiles()).thenReturn(new String[]{"prod"});

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> notificationsService.sendSMS(sms));
        assertTrue(exception.getMessage().contains("Failed to send SMS"));
    }

    @Test
    void verifyIdentity_Error() throws Exception {
        // Given
        String phone = "+212600000000";
        String code = "123456";

        // Configure environment to not be test
        when(environment.getActiveProfiles()).thenReturn(new String[]{"prod"});

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, 
            () -> notificationsService.verifyIdentity(phone, code));
        assertTrue(exception.getMessage().contains("Failed to verify code") || 
                  exception.getMessage().contains("Failed to verify identity"));
    }
}
