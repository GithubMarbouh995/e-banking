package com.ebanking.notificationsservice.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class KYCTest {

    @Test
    void testKYCBuilder() {
        // Arrange & Act
        KYC kyc = KYC.builder()
                .id(1L)
                .title("Mr")
                .firstName("John")
                .lastName("Doe")
                .idType("CIN")
                .countryOfIssue("Morocco")
                .idNumber("AB123456")
                .idExpirationDate("2025-12-31")
                .idValidityDate("2015-01-01")
                .dateOfBirth("1990-01-01")
                .profession("Engineer")
                .nationality("Moroccan")
                .countryOfAddress("Morocco")
                .legalAddress("123 Main St")
                .city("Casablanca")
                .gsm("+212600000000")
                .email("john.doe@example.com")
                .build();

        // Assert
        assertEquals(1L, kyc.getId());
        assertEquals("Mr", kyc.getTitle());
        assertEquals("John", kyc.getFirstName());
        assertEquals("Doe", kyc.getLastName());
        assertEquals("CIN", kyc.getIdType());
        assertEquals("Morocco", kyc.getCountryOfIssue());
        assertEquals("AB123456", kyc.getIdNumber());
        assertEquals("2025-12-31", kyc.getIdExpirationDate());
        assertEquals("2015-01-01", kyc.getIdValidityDate());
        assertEquals("1990-01-01", kyc.getDateOfBirth());
        assertEquals("Engineer", kyc.getProfession());
        assertEquals("Moroccan", kyc.getNationality());
        assertEquals("Morocco", kyc.getCountryOfAddress());
        assertEquals("123 Main St", kyc.getLegalAddress());
        assertEquals("Casablanca", kyc.getCity());
        assertEquals("+212600000000", kyc.getGsm());
        assertEquals("john.doe@example.com", kyc.getEmail());
    }

    @Test
    void testNoArgsConstructor() {
        // Act
        KYC kyc = new KYC();

        // Assert
        assertNull(kyc.getId());
        assertNull(kyc.getTitle());
        assertNull(kyc.getFirstName());
        assertNull(kyc.getLastName());
        assertNull(kyc.getIdType());
        assertNull(kyc.getCountryOfIssue());
        assertNull(kyc.getIdNumber());
        assertNull(kyc.getIdExpirationDate());
        assertNull(kyc.getIdValidityDate());
        assertNull(kyc.getDateOfBirth());
        assertNull(kyc.getProfession());
        assertNull(kyc.getNationality());
        assertNull(kyc.getCountryOfAddress());
        assertNull(kyc.getLegalAddress());
        assertNull(kyc.getCity());
        assertNull(kyc.getGsm());
        assertNull(kyc.getEmail());
        assertNull(kyc.getCustomer());
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        KYC kyc1 = KYC.builder()
                .id(1L)
                .title("Mr")
                .firstName("John")
                .lastName("Doe")
                .idNumber("AB123456")
                .build();

        KYC kyc2 = KYC.builder()
                .id(1L)
                .title("Mr")
                .firstName("John")
                .lastName("Doe")
                .idNumber("AB123456")
                .build();

        KYC kyc3 = KYC.builder()
                .id(2L)
                .title("Mrs")
                .firstName("Jane")
                .lastName("Smith")
                .idNumber("CD789012")
                .build();

        // Assert
        assertEquals(kyc1, kyc2);
        assertEquals(kyc1.hashCode(), kyc2.hashCode());
        assertNotEquals(kyc1, kyc3);
        assertNotEquals(kyc1.hashCode(), kyc3.hashCode());
    }

    @Test
    void testToString() {
        // Arrange
        KYC kyc = KYC.builder()
                .id(1L)
                .title("Mr")
                .firstName("John")
                .lastName("Doe")
                .idNumber("AB123456")
                .build();

        // Act
        String toString = kyc.toString();

        // Assert
        assertTrue(toString.contains("id=1"));
        assertTrue(toString.contains("title=Mr"));
        assertTrue(toString.contains("firstName=John"));
        assertTrue(toString.contains("lastName=Doe"));
        assertTrue(toString.contains("idNumber=AB123456"));
    }

    @Test
    void testCustomerRelationship() {
        // Arrange
        KYC kyc = new KYC();
        Customer customer = new Customer();

        // Act
        kyc.setCustomer(customer);

        // Assert
        assertNotNull(kyc.getCustomer());
        assertEquals(customer, kyc.getCustomer());
    }

    @Test
    void testAllArgsConstructor() {
        // Arrange
        Long id = 1L;
        String title = "Mr";
        String firstName = "John";
        String lastName = "Doe";
        String idType = "CIN";
        String countryOfIssue = "Morocco";
        String idNumber = "AB123456";
        String idExpirationDate = "2025-12-31";
        String idValidityDate = "2015-01-01";
        String dateOfBirth = "1990-01-01";
        String profession = "Engineer";
        String nationality = "Moroccan";
        String countryOfAddress = "Morocco";
        String legalAddress = "123 Main St";
        String city = "Casablanca";
        String gsm = "+212600000000";
        String email = "john.doe@example.com";
        Customer customer = new Customer();

        // Act
        KYC kyc = new KYC(id, title, firstName, lastName, idType, countryOfIssue,
                idNumber, idExpirationDate, idValidityDate, dateOfBirth, profession,
                nationality, countryOfAddress, legalAddress, city, gsm, email, customer);

        // Assert
        assertEquals(id, kyc.getId());
        assertEquals(title, kyc.getTitle());
        assertEquals(firstName, kyc.getFirstName());
        assertEquals(lastName, kyc.getLastName());
        assertEquals(idType, kyc.getIdType());
        assertEquals(countryOfIssue, kyc.getCountryOfIssue());
        assertEquals(idNumber, kyc.getIdNumber());
        assertEquals(idExpirationDate, kyc.getIdExpirationDate());
        assertEquals(idValidityDate, kyc.getIdValidityDate());
        assertEquals(dateOfBirth, kyc.getDateOfBirth());
        assertEquals(profession, kyc.getProfession());
        assertEquals(nationality, kyc.getNationality());
        assertEquals(countryOfAddress, kyc.getCountryOfAddress());
        assertEquals(legalAddress, kyc.getLegalAddress());
        assertEquals(city, kyc.getCity());
        assertEquals(gsm, kyc.getGsm());
        assertEquals(email, kyc.getEmail());
        assertEquals(customer, kyc.getCustomer());
    }
}
