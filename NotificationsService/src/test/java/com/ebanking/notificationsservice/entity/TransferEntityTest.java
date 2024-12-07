package com.ebanking.notificationsservice.entity;

import com.ebanking.notificationsservice.model.TransferState;
import com.ebanking.notificationsservice.model.TransferType;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class TransferEntityTest {

    @Test
    void testTransferEntityBuilder() {
        // Arrange
        Customer customer = new Customer();
        Wallet wallet = new Wallet();
        List<Beneficiary> beneficiaries = new ArrayList<>();
        byte[] pdfContent = "Test PDF Content".getBytes();

        // Act
        TransferEntity transfer = TransferEntity.builder()
                .id(1L)
                .reference("REF123")
                .amount(1000.0)
                .maxBAmountPeriodC(5000.0)
                .maxTransfersPerCustomer(3)
                .toBeServedFrom("2024-01-01")
                .PINCode("1234")
                .maxPIN_Attempts(3)
                .validationDuration(24)
                .initiatedAt("2024-01-01 10:00:00")
                .receiptUrl("http://example.com/receipt.pdf")
                .pdfContent(pdfContent)
                .benefeicaryID(100L)
                .customer(customer)
                .motif("Test transfer")
                .state(TransferState.TO_BE_SERVED)
                .type(TransferType.BANK_TO_GAB)
                .wallet(wallet)
                .beneficiaries(beneficiaries)
                .customerWalletId(200L)
                .beneficiaryWalletId(300L)
                .build();

        // Assert
        assertEquals(1L, transfer.getId());
        assertEquals("REF123", transfer.getReference());
        assertEquals(1000.0, transfer.getAmount());
        assertEquals(5000.0, transfer.getMaxBAmountPeriodC());
        assertEquals(3, transfer.getMaxTransfersPerCustomer());
        assertEquals("2024-01-01", transfer.getToBeServedFrom());
        assertEquals("1234", transfer.getPINCode());
        assertEquals(3, transfer.getMaxPIN_Attempts());
        assertEquals(24, transfer.getValidationDuration());
        assertEquals("2024-01-01 10:00:00", transfer.getInitiatedAt());
        assertEquals("http://example.com/receipt.pdf", transfer.getReceiptUrl());
        assertArrayEquals(pdfContent, transfer.getPdfContent());
        assertEquals(100L, transfer.getBenefeicaryID());
        assertEquals(customer, transfer.getCustomer());
        assertEquals("Test transfer", transfer.getMotif());
        assertEquals(TransferState.TO_BE_SERVED, transfer.getState());
        assertEquals(TransferType.BANK_TO_GAB, transfer.getType());
        assertEquals(wallet, transfer.getWallet());
        assertEquals(beneficiaries, transfer.getBeneficiaries());
        assertEquals(200L, transfer.getCustomerWalletId());
        assertEquals(300L, transfer.getBeneficiaryWalletId());
    }

    @Test
    void testNoArgsConstructor() {
        // Act
        TransferEntity transfer = new TransferEntity();

        // Assert
        assertNull(transfer.getId());
        assertNull(transfer.getReference());
        assertEquals(0.0, transfer.getAmount());
        assertEquals(0.0, transfer.getMaxBAmountPeriodC());
        assertEquals(0, transfer.getMaxTransfersPerCustomer());
        assertNull(transfer.getToBeServedFrom());
        assertNull(transfer.getPINCode());
        assertEquals(0, transfer.getMaxPIN_Attempts());
        assertEquals(0, transfer.getValidationDuration());
        assertNull(transfer.getInitiatedAt());
        assertNull(transfer.getReceiptUrl());
        assertNull(transfer.getPdfContent());
        assertNull(transfer.getBenefeicaryID());
        assertNull(transfer.getCustomer());
        assertNull(transfer.getMotif());
        assertNull(transfer.getState());
        assertNull(transfer.getType());
        assertNull(transfer.getWallet());
        assertNull(transfer.getBeneficiaries());
        assertNull(transfer.getCustomerWalletId());
        assertNull(transfer.getBeneficiaryWalletId());
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        TransferEntity transfer1 = TransferEntity.builder()
                .id(1L)
                .reference("REF123")
                .amount(1000.0)
                .state(TransferState.TO_BE_SERVED)
                .type(TransferType.BANK_TO_GAB)
                .build();

        TransferEntity transfer2 = TransferEntity.builder()
                .id(1L)
                .reference("REF123")
                .amount(1000.0)
                .state(TransferState.TO_BE_SERVED)
                .type(TransferType.BANK_TO_GAB)
                .build();

        TransferEntity transfer3 = TransferEntity.builder()
                .id(2L)
                .reference("REF456")
                .amount(2000.0)
                .state(TransferState.SERVED)
                .type(TransferType.BANK_TO_BANK)
                .build();

        // Assert
        assertEquals(transfer1, transfer2);
        assertEquals(transfer1.hashCode(), transfer2.hashCode());
        assertNotEquals(transfer1, transfer3);
        assertNotEquals(transfer1.hashCode(), transfer3.hashCode());
    }

    @Test
    void testToString() {
        // Arrange
        TransferEntity transfer = TransferEntity.builder()
                .id(1L)
                .reference("REF123")
                .amount(1000.0)
                .state(TransferState.TO_BE_SERVED)
                .type(TransferType.BANK_TO_GAB)
                .build();

        // Act
        String toString = transfer.toString();

        // Assert
        assertTrue(toString.contains("id=1"));
        assertTrue(toString.contains("reference=REF123"));
        assertTrue(toString.contains("amount=1000.0"));
        assertTrue(toString.contains("state=TO_BE_SERVED"));
        assertTrue(toString.contains("type=BANK_TO_GAB"));
    }

    @Test
    void testRelationshipInitialization() {
        // Arrange
        TransferEntity transfer = new TransferEntity();
        Customer customer = new Customer();
        Wallet wallet = new Wallet();
        List<Beneficiary> beneficiaries = new ArrayList<>();
        Beneficiary beneficiary = new Beneficiary();
        beneficiaries.add(beneficiary);

        // Act
        transfer.setCustomer(customer);
        transfer.setWallet(wallet);
        transfer.setBeneficiaries(beneficiaries);

        // Assert
        assertEquals(customer, transfer.getCustomer());
        assertEquals(wallet, transfer.getWallet());
        assertEquals(1, transfer.getBeneficiaries().size());
        assertEquals(beneficiary, transfer.getBeneficiaries().get(0));
    }
}
