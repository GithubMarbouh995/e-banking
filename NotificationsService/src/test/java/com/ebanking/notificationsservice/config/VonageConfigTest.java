package com.ebanking.notificationsservice.config;

import com.vonage.client.VonageClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig
@Import(VonageConfigTest.TestConfig.class)
class VonageConfigTest {

    @TestConfiguration
    static class TestConfig {
        @Bean
        public VonageConfig vonageConfig() {
            return new VonageConfig();
        }
    }

    @Autowired
    private VonageConfig vonageConfig;

    @Test
    void testVonageClientCreation() {
        // Set the required properties manually
        setField(vonageConfig, "apiKey", "test-api-key");
        setField(vonageConfig, "apiSecret", "test-api-secret");

        // Act
        VonageClient vonageClient = vonageConfig.vonageClient();

        // Assert
        assertNotNull(vonageClient, "VonageClient should not be null");
    }

    @Test
    void testVonageConfigProperties() {
        // Set the properties manually
        setField(vonageConfig, "apiKey", "test-api-key");
        setField(vonageConfig, "apiSecret", "test-api-secret");

        // Assert using reflection
        assertEquals("test-api-key", getField(vonageConfig, "apiKey"),
            "API Key should match the test value");
        assertEquals("test-api-secret", getField(vonageConfig, "apiSecret"),
            "API Secret should match the test value");
    }

    private void setField(Object target, String fieldName, String value) {
        try {
            var field = VonageConfig.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(target, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail("Failed to set field " + fieldName + ": " + e.getMessage());
        }
    }

    private String getField(Object target, String fieldName) {
        try {
            var field = VonageConfig.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            return (String) field.get(target);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail("Failed to get field " + fieldName + ": " + e.getMessage());
            return null;
        }
    }
}
