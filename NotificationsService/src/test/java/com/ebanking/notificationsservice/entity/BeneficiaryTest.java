package com.ebanking.notificationsservice.entity;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class BeneficiaryTest {

    @Test
    void testBeneficiaryBuilder() {
        // Arrange
        Customer customer = new Customer();
        TransferEntity transferEntity = new TransferEntity();
        List<Wallet> wallets = new ArrayList<>();
        
        // Act
        Beneficiary beneficiary = Beneficiary.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .phone("+212600000000")
                .rib("123456789012345678901234")
                .cin("AB123456")
                .customerID(100L)
                .customer(customer)
                .transferEntity(transferEntity)
                .wallets(wallets)
                .transferID(200L)
                .build();

        // Assert
        assertEquals(1L, beneficiary.getId());
        assertEquals("John", beneficiary.getFirstName());
        assertEquals("Doe", beneficiary.getLastName());
        assertEquals("+212600000000", beneficiary.getPhone());
        assertEquals("123456789012345678901234", beneficiary.getRib());
        assertEquals("AB123456", beneficiary.getCin());
        assertEquals(100L, beneficiary.getCustomerID());
        assertEquals(customer, beneficiary.getCustomer());
        assertEquals(transferEntity, beneficiary.getTransferEntity());
        assertEquals(wallets, beneficiary.getWallets());
        assertEquals(200L, beneficiary.getTransferID());
    }

    @Test
    void testNoArgsConstructor() {
        // Act
        Beneficiary beneficiary = new Beneficiary();

        // Assert
        assertNull(beneficiary.getId());
        assertNull(beneficiary.getFirstName());
        assertNull(beneficiary.getLastName());
        assertNull(beneficiary.getPhone());
        assertNull(beneficiary.getRib());
        assertNull(beneficiary.getCin());
        assertNull(beneficiary.getCustomerID());
        assertNull(beneficiary.getCustomer());
        assertNull(beneficiary.getTransferEntity());
        assertNull(beneficiary.getWallets());
        assertEquals(0L, beneficiary.getTransferID());
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        Beneficiary beneficiary1 = Beneficiary.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .phone("+212600000000")
                .rib("123456789012345678901234")
                .cin("AB123456")
                .build();

        Beneficiary beneficiary2 = Beneficiary.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .phone("+212600000000")
                .rib("123456789012345678901234")
                .cin("AB123456")
                .build();

        Beneficiary beneficiary3 = Beneficiary.builder()
                .id(2L)
                .firstName("Jane")
                .lastName("Smith")
                .phone("+212611111111")
                .rib("987654321098765432109876")
                .cin("CD789012")
                .build();

        // Assert
        assertEquals(beneficiary1, beneficiary2);
        assertEquals(beneficiary1.hashCode(), beneficiary2.hashCode());
        assertNotEquals(beneficiary1, beneficiary3);
        assertNotEquals(beneficiary1.hashCode(), beneficiary3.hashCode());
    }

    @Test
    void testToString() {
        // Arrange
        Beneficiary beneficiary = Beneficiary.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .phone("+212600000000")
                .rib("123456789012345678901234")
                .cin("AB123456")
                .build();

        // Act
        String toString = beneficiary.toString();

        // Assert
        assertTrue(toString.contains("id=1"));
        assertTrue(toString.contains("firstName=John"));
        assertTrue(toString.contains("lastName=Doe"));
        assertTrue(toString.contains("phone=+212600000000"));
        assertTrue(toString.contains("rib=123456789012345678901234"));
        assertTrue(toString.contains("cin=AB123456"));
    }

    @Test
    void testRelationshipInitialization() {
        // Arrange
        Beneficiary beneficiary = new Beneficiary();
        Customer customer = new Customer();
        TransferEntity transferEntity = new TransferEntity();
        List<Wallet> wallets = new ArrayList<>();
        Wallet wallet = new Wallet();
        wallets.add(wallet);

        // Act
        beneficiary.setCustomer(customer);
        beneficiary.setTransferEntity(transferEntity);
        beneficiary.setWallets(wallets);

        // Assert
        assertEquals(customer, beneficiary.getCustomer());
        assertEquals(transferEntity, beneficiary.getTransferEntity());
        assertEquals(1, beneficiary.getWallets().size());
        assertEquals(wallet, beneficiary.getWallets().get(0));
    }
}
