package com.ebanking.notificationsservice.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {

    @Test
    void testBuilder() {
        // Arrange & Act
        Customer customer = Customer.builder()
                .firstName("John")
                .lastname("Doe")
                .phone("+212600000000")
                .build();

        // Assert
        assertEquals("John", customer.getFirstName());
        assertEquals("Doe", customer.getLastname());
        assertEquals("+212600000000", customer.getPhone());
    }

    @Test
    void testNoArgsConstructor() {
        // Act
        Customer customer = new Customer();

        // Assert
        assertNull(customer.getFirstName());
        assertNull(customer.getLastname());
        assertNull(customer.getPhone());
    }

    @Test
    void testAllArgsConstructor() {
        // Arrange
        String firstName = "Jane";
        String lastName = "Smith";
        String phone = "+212611111111";

        // Act
        Customer customer = new Customer(firstName, lastName, phone);

        // Assert
        assertEquals(firstName, customer.getFirstName());
        assertEquals(lastName, customer.getLastname());
        assertEquals(phone, customer.getPhone());
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        Customer customer1 = Customer.builder()
                .firstName("John")
                .lastname("Doe")
                .phone("+212600000000")
                .build();

        Customer customer2 = Customer.builder()
                .firstName("John")
                .lastname("Doe")
                .phone("+212600000000")
                .build();

        Customer customer3 = Customer.builder()
                .firstName("Jane")
                .lastname("Smith")
                .phone("+212611111111")
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
                .firstName("John")
                .lastname("Doe")
                .phone("+212600000000")
                .build();

        // Act
        String toString = customer.toString();

        // Assert
        assertTrue(toString.contains("firstName=John"));
        assertTrue(toString.contains("lastname=Doe"));
        assertTrue(toString.contains("phone=+212600000000"));
    }

    @Test
    void testSettersAndGetters() {
        // Arrange
        Customer customer = new Customer();

        // Act
        customer.setFirstName("Alice");
        customer.setLastname("Johnson");
        customer.setPhone("+212622222222");

        // Assert
        assertEquals("Alice", customer.getFirstName());
        assertEquals("Johnson", customer.getLastname());
        assertEquals("+212622222222", customer.getPhone());
    }
}
