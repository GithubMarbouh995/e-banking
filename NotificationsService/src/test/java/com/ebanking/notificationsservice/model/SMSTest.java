package com.ebanking.notificationsservice.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SMSTest {

    @Test
    void testSMSBuilder() {
        // Arrange & Act
        SMS sms = SMS.builder()
                .transferType(TransferType.BANK_TO_GAB)
                .amount(1000.0)
                .pin("1234")
                .ref("REF123")
                .phone("+212600000000")
                .sendRef(true)
                .customerFirstName("John")
                .customerLastName("Doe")
                .beneficiaryFirstName("Jane")
                .beneficiaryLastName("Smith")
                .build();

        // Assert
        assertEquals(TransferType.BANK_TO_GAB, sms.getTransferType());
        assertEquals(1000.0, sms.getAmount());
        assertEquals("1234", sms.getPin());
        assertEquals("REF123", sms.getRef());
        assertEquals("+212600000000", sms.getPhone());
        assertTrue(sms.getSendRef());
        assertEquals("John", sms.getCustomerFirstName());
        assertEquals("Doe", sms.getCustomerLastName());
        assertEquals("Jane", sms.getBeneficiaryFirstName());
        assertEquals("Smith", sms.getBeneficiaryLastName());
    }

    @Test
    void testNoArgsConstructor() {
        // Act
        SMS sms = new SMS();

        // Assert
        assertNull(sms.getTransferType());
        assertEquals(0.0, sms.getAmount());
        assertNull(sms.getPin());
        assertNull(sms.getRef());
        assertNull(sms.getPhone());
        assertNull(sms.getSendRef());
        assertNull(sms.getCustomerFirstName());
        assertNull(sms.getCustomerLastName());
        assertNull(sms.getBeneficiaryFirstName());
        assertNull(sms.getBeneficiaryLastName());
    }

    @Test
    void testAllArgsConstructor() {
        // Arrange
        TransferType transferType = TransferType.BANK_TO_BANK;
        double amount = 500.0;
        String pin = "5678";
        String ref = "REF456";
        String phone = "+212611111111";
        Boolean sendRef = false;
        String customerFirstName = "Alice";
        String customerLastName = "Johnson";
        String beneficiaryFirstName = "Bob";
        String beneficiaryLastName = "Williams";

        // Act
        SMS sms = new SMS(transferType, amount, pin, ref, phone, sendRef,
                customerFirstName, customerLastName, beneficiaryFirstName, beneficiaryLastName);

        // Assert
        assertEquals(transferType, sms.getTransferType());
        assertEquals(amount, sms.getAmount());
        assertEquals(pin, sms.getPin());
        assertEquals(ref, sms.getRef());
        assertEquals(phone, sms.getPhone());
        assertFalse(sms.getSendRef());
        assertEquals(customerFirstName, sms.getCustomerFirstName());
        assertEquals(customerLastName, sms.getCustomerLastName());
        assertEquals(beneficiaryFirstName, sms.getBeneficiaryFirstName());
        assertEquals(beneficiaryLastName, sms.getBeneficiaryLastName());
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        SMS sms1 = SMS.builder()
                .transferType(TransferType.BANK_TO_GAB)
                .amount(1000.0)
                .pin("1234")
                .ref("REF123")
                .phone("+212600000000")
                .sendRef(true)
                .customerFirstName("John")
                .customerLastName("Doe")
                .beneficiaryFirstName("Jane")
                .beneficiaryLastName("Smith")
                .build();

        SMS sms2 = SMS.builder()
                .transferType(TransferType.BANK_TO_GAB)
                .amount(1000.0)
                .pin("1234")
                .ref("REF123")
                .phone("+212600000000")
                .sendRef(true)
                .customerFirstName("John")
                .customerLastName("Doe")
                .beneficiaryFirstName("Jane")
                .beneficiaryLastName("Smith")
                .build();

        SMS sms3 = SMS.builder()
                .transferType(TransferType.BANK_TO_BANK)
                .amount(2000.0)
                .pin("5678")
                .build();

        // Assert
        assertEquals(sms1, sms2);
        assertEquals(sms1.hashCode(), sms2.hashCode());
        assertNotEquals(sms1, sms3);
        assertNotEquals(sms1.hashCode(), sms3.hashCode());
    }

    @Test
    void testToString() {
        // Arrange
        SMS sms = SMS.builder()
                .transferType(TransferType.BANK_TO_GAB)
                .amount(1000.0)
                .pin("1234")
                .ref("REF123")
                .build();

        // Act
        String toString = sms.toString();

        // Assert
        assertTrue(toString.contains("transferType=BANK_TO_GAB"));
        assertTrue(toString.contains("amount=1000.0"));
        assertTrue(toString.contains("pin=1234"));
        assertTrue(toString.contains("ref=REF123"));
    }
}