package com.ebanking.notificationsservice.service;

import com.ebanking.notificationsservice.model.SMS;
import com.ebanking.notificationsservice.model.SendVerificationCodeResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class NotificationsServiceImplIntegrationTest {

    @Autowired
    private NotificationsService notificationsService;

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

        // When
        String result = notificationsService.sendSMS(sms);

        // Then
        assertTrue(result.contains("successfully"));
    }

    @Test
    void verifyIdentity_Success() {
        // Given
        String phone = "+212600000000";
        String code = "123456";

        // When
        SendVerificationCodeResponse result = notificationsService.verifyIdentity(phone, code);

        // Then
        assertNotNull(result);
        assertEquals(code, result.getCode());
        assertTrue(result.getMessage().contains(code));
    }
}
