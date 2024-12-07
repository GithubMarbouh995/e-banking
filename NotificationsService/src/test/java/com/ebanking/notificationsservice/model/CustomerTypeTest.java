package com.ebanking.notificationsservice.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CustomerTypeTest {

    @Test
    void testEnumValues() {
        // Act
        CustomerType[] types = CustomerType.values();

        // Assert
        assertEquals(2, types.length);
        assertEquals(CustomerType.PROSPECT, types[0]);
        assertEquals(CustomerType.EXISTING, types[1]);
    }

    @Test
    void testEnumValueOf() {
        // Act & Assert
        assertEquals(CustomerType.PROSPECT, CustomerType.valueOf("PROSPECT"));
        assertEquals(CustomerType.EXISTING, CustomerType.valueOf("EXISTING"));
    }

    @Test
    void testInvalidEnumValue() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> CustomerType.valueOf("INVALID_TYPE"));
    }

    @Test
    void testEnumOrdinal() {
        // Act & Assert
        assertEquals(0, CustomerType.PROSPECT.ordinal());
        assertEquals(1, CustomerType.EXISTING.ordinal());
    }
}
