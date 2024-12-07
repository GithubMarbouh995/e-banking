package com.ebanking.notificationsservice.entity;

import com.ebanking.notificationsservice.model.CustomerType;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {

    @Test
    void testCustomerBuilder() {
        // Arrange
        KYC kyc = new KYC();
        List<TransferEntity> transactions = new ArrayList<>();
        List<Wallet> wallets = new ArrayList<>();

        // Act
        Customer customer = Customer.builder()
                .id(1L)
                .cin("AB123456")
                .firstName("John")
                .lastName("Doe")
                .phone("+212600000000")
                .kyc(kyc)
                .transactions(transactions)
                .wallets(wallets)
                .customerType(CustomerType.EXISTING)
                .otp("123456")
                .ctc(123456L)
                .rib("123456789012345678901234")
                .build();

        // Assert
        assertEquals(1L, customer.getId());
        assertEquals("AB123456", customer.getCin());
        assertEquals("John", customer.getFirstName());
        assertEquals("Doe", customer.getLastName());
        assertEquals("+212600000000", customer.getPhone());
        assertEquals(kyc, customer.getKyc());
        assertEquals(transactions, customer.getTransactions());
        assertEquals(wallets, customer.getWallets());
        assertEquals(CustomerType.EXISTING, customer.getCustomerType());
        assertEquals("123456", customer.getOtp());
        assertEquals(123456L, customer.getCtc());
        assertEquals("123456789012345678901234", customer.getRib());
    }

    @Test
    void testCustomerConstructorWithParameters() {
        // Arrange & Act
        Customer customer = new Customer(
                "John",
                "Doe",
                "+212600000000",
                "AB123456",
                CustomerType.PROSPECT
        );

        // Assert
        assertEquals("John", customer.getFirstName());
        assertEquals("Doe", customer.getLastName());
        assertEquals("+212600000000", customer.getPhone());
        assertEquals("AB123456", customer.getCin());
        assertEquals(CustomerType.PROSPECT, customer.getCustomerType());
    }

    @Test
    void testNoArgsConstructor() {
        // Act
        Customer customer = new Customer();

        // Assert
        assertNull(customer.getId());
        assertNull(customer.getCin());
        assertNull(customer.getFirstName());
        assertNull(customer.getLastName());
        assertNull(customer.getPhone());
        assertNull(customer.getKyc());
        assertNull(customer.getTransactions());
        assertNull(customer.getWallets());
        assertNull(customer.getCustomerType());
        assertNull(customer.getOtp());
        assertNull(customer.getCtc());
        assertNull(customer.getRib());
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        Customer customer1 = Customer.builder()
                .id(1L)
                .cin("AB123456")
                .firstName("John")
                .lastName("Doe")
                .phone("+212600000000")
                .customerType(CustomerType.EXISTING)
                .build();

        Customer customer2 = Customer.builder()
                .id(1L)
                .cin("AB123456")
                .firstName("John")
                .lastName("Doe")
                .phone("+212600000000")
                .customerType(CustomerType.EXISTING)
                .build();

        Customer customer3 = Customer.builder()
                .id(2L)
                .cin("CD789012")
                .firstName("Jane")
                .lastName("Smith")
                .phone("+212611111111")
                .customerType(CustomerType.PROSPECT)
                .build();

        // Assert
        assertEquals(customer1, customer2);
        assertEquals(customer1.hashCode(), customer2.hashCode());
        assertNotEquals(customer1, customer3);
        assertNotEquals(customer1.hashCode(), customer3.hashCode());
    }

    @Test
    void testToString() {
        // Arrange
        Customer customer = Customer.builder()
                .id(1L)
                .cin("AB123456")
                .firstName("John")
                .lastName("Doe")
                .phone("+212600000000")
                .customerType(CustomerType.EXISTING)
                .build();

        // Act
        String toString = customer.toString();

        // Assert
        assertTrue(toString.contains("id=1"));
        assertTrue(toString.contains("cin=AB123456"));
        assertTrue(toString.contains("firstName=John"));
        assertTrue(toString.contains("lastName=Doe"));
        assertTrue(toString.contains("phone=+212600000000"));
        assertTrue(toString.contains("customerType=EXISTING"));
    }

    @Test
    void testRelationshipInitialization() {
        // Arrange
        Customer customer = new Customer();
        TransferEntity transfer = new TransferEntity();
        Wallet wallet = new Wallet();
        List<TransferEntity> transactions = new ArrayList<>();
        List<Wallet> wallets = new ArrayList<>();
        transactions.add(transfer);
        wallets.add(wallet);

        // Act
        customer.setTransactions(transactions);
        customer.setWallets(wallets);

        // Assert
        assertEquals(1, customer.getTransactions().size());
        assertEquals(1, customer.getWallets().size());
        assertEquals(transfer, customer.getTransactions().get(0));
        assertEquals(wallet, customer.getWallets().get(0));
    }

    @Test
    void testKYCRelationship() {
        // Arrange
        Customer customer = new Customer();
        KYC kyc = new KYC();

        // Act
        customer.setKyc(kyc);

        // Assert
        assertNotNull(customer.getKyc());
        assertEquals(kyc, customer.getKyc());
    }
}
