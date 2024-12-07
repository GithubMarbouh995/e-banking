package com.ebanking.notificationsservice.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class WalletTest {

    @Test
    void testWalletBuilder() {
        // Arrange
        Customer customer = new Customer();
        TransferEntity transfer = new TransferEntity();
        Beneficiary beneficiary = new Beneficiary();

        // Act
        Wallet wallet = Wallet.builder()
                .id(1L)
                .account_number("123456789")
                .rib("123456789012345678901234")
                .balance(1000.0)
                .customer(customer)
                .transfer(transfer)
                .beneficiary(beneficiary)
                .build();

        // Assert
        assertEquals(1L, wallet.getId());
        assertEquals("123456789", wallet.getAccount_number());
        assertEquals("123456789012345678901234", wallet.getRib());
        assertEquals(1000.0, wallet.getBalance());
        assertEquals(customer, wallet.getCustomer());
        assertEquals(transfer, wallet.getTransfer());
        assertEquals(beneficiary, wallet.getBeneficiary());
    }

    @Test
    void testNoArgsConstructor() {
        // Act
        Wallet wallet = new Wallet();

        // Assert
        assertNull(wallet.getId());
        assertNull(wallet.getAccount_number());
        assertNull(wallet.getRib());
        assertEquals(0.0, wallet.getBalance());
        assertNull(wallet.getCustomer());
        assertNull(wallet.getTransfer());
        assertNull(wallet.getBeneficiary());
    }

    @Test
    void testAllArgsConstructor() {
        // Arrange
        Long id = 1L;
        String accountNumber = "123456789";
        String rib = "123456789012345678901234";
        double balance = 1000.0;
        Customer customer = new Customer();
        TransferEntity transfer = new TransferEntity();
        Beneficiary beneficiary = new Beneficiary();

        // Act
        Wallet wallet = new Wallet(id, accountNumber, rib, balance, customer, transfer, beneficiary);

        // Assert
        assertEquals(id, wallet.getId());
        assertEquals(accountNumber, wallet.getAccount_number());
        assertEquals(rib, wallet.getRib());
        assertEquals(balance, wallet.getBalance());
        assertEquals(customer, wallet.getCustomer());
        assertEquals(transfer, wallet.getTransfer());
        assertEquals(beneficiary, wallet.getBeneficiary());
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        Wallet wallet1 = Wallet.builder()
                .id(1L)
                .account_number("123456789")
                .rib("123456789012345678901234")
                .balance(1000.0)
                .build();

        Wallet wallet2 = Wallet.builder()
                .id(1L)
                .account_number("123456789")
                .rib("123456789012345678901234")
                .balance(1000.0)
                .build();

        Wallet wallet3 = Wallet.builder()
                .id(2L)
                .account_number("987654321")
                .rib("987654321098765432109876")
                .balance(2000.0)
                .build();

        // Assert
        assertEquals(wallet1, wallet2);
        assertEquals(wallet1.hashCode(), wallet2.hashCode());
        assertNotEquals(wallet1, wallet3);
        assertNotEquals(wallet1.hashCode(), wallet3.hashCode());
    }

    @Test
    void testToString() {
        // Arrange
        Wallet wallet = Wallet.builder()
                .id(1L)
                .account_number("123456789")
                .rib("123456789012345678901234")
                .balance(1000.0)
                .build();

        // Act
        String toString = wallet.toString();

        // Assert
        assertTrue(toString.contains("id=1"));
        assertTrue(toString.contains("account_number=123456789"));
        assertTrue(toString.contains("rib=123456789012345678901234"));
        assertTrue(toString.contains("balance=1000.0"));
    }

    @Test
    void testRelationshipInitialization() {
        // Arrange
        Wallet wallet = new Wallet();
        Customer customer = new Customer();
        TransferEntity transfer = new TransferEntity();
        Beneficiary beneficiary = new Beneficiary();

        // Act
        wallet.setCustomer(customer);
        wallet.setTransfer(transfer);
        wallet.setBeneficiary(beneficiary);

        // Assert
        assertEquals(customer, wallet.getCustomer());
        assertEquals(transfer, wallet.getTransfer());
        assertEquals(beneficiary, wallet.getBeneficiary());
    }
}
