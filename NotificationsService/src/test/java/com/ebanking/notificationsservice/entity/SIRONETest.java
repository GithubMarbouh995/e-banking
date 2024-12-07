package com.ebanking.notificationsservice.entity;

import org.junit.jupiter.api.Test;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

class SIRONETest {

    @Test
    void testSIRONEBuilder() {
        // Arrange
        Date birthDate = new Date();

        // Act
        SIRONE sirone = SIRONE.builder()
                .id(1L)
                .cin("AB123456")
                .reason("Test reason")
                .rib("123456789012345678901234")
                .birth(birthDate)
                .phone("+212600000000")
                .build();

        // Assert
        assertEquals(1L, sirone.getId());
        assertEquals("AB123456", sirone.getCin());
        assertEquals("Test reason", sirone.getReason());
        assertEquals("123456789012345678901234", sirone.getRib());
        assertEquals(birthDate, sirone.getBirth());
        assertEquals("+212600000000", sirone.getPhone());
    }

    @Test
    void testNoArgsConstructor() {
        // Act
        SIRONE sirone = new SIRONE();

        // Assert
        assertNull(sirone.getId());
        assertNull(sirone.getCin());
        assertNull(sirone.getReason());
        assertNull(sirone.getRib());
        assertNull(sirone.getBirth());
        assertNull(sirone.getPhone());
    }

    @Test
    void testAllArgsConstructor() {
        // Arrange
        Long id = 1L;
        String cin = "AB123456";
        String reason = "Test reason";
        String rib = "123456789012345678901234";
        Date birth = new Date();
        String phone = "+212600000000";

        // Act
        SIRONE sirone = new SIRONE(id, cin, reason, rib, birth, phone);

        // Assert
        assertEquals(id, sirone.getId());
        assertEquals(cin, sirone.getCin());
        assertEquals(reason, sirone.getReason());
        assertEquals(rib, sirone.getRib());
        assertEquals(birth, sirone.getBirth());
        assertEquals(phone, sirone.getPhone());
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        Date birthDate = new Date();

        SIRONE sirone1 = SIRONE.builder()
                .id(1L)
                .cin("AB123456")
                .reason("Test reason")
                .rib("123456789012345678901234")
                .birth(birthDate)
                .phone("+212600000000")
                .build();

        SIRONE sirone2 = SIRONE.builder()
                .id(1L)
                .cin("AB123456")
                .reason("Test reason")
                .rib("123456789012345678901234")
                .birth(birthDate)
                .phone("+212600000000")
                .build();

        SIRONE sirone3 = SIRONE.builder()
                .id(2L)
                .cin("CD789012")
                .reason("Different reason")
                .rib("987654321098765432109876")
                .birth(new Date(birthDate.getTime() + 86400000)) // Add one day
                .phone("+212611111111")
                .build();

        // Assert
        assertEquals(sirone1, sirone2);
        assertEquals(sirone1.hashCode(), sirone2.hashCode());
        assertNotEquals(sirone1, sirone3);
        assertNotEquals(sirone1.hashCode(), sirone3.hashCode());
    }

    @Test
    void testToString() {
        // Arrange
        Date birthDate = new Date();
        SIRONE sirone = SIRONE.builder()
                .id(1L)
                .cin("AB123456")
                .reason("Test reason")
                .rib("123456789012345678901234")
                .birth(birthDate)
                .phone("+212600000000")
                .build();

        // Act
        String toString = sirone.toString();

        // Assert
        assertTrue(toString.contains("id=1"));
        assertTrue(toString.contains("cin=AB123456"));
        assertTrue(toString.contains("reason=Test reason"));
        assertTrue(toString.contains("rib=123456789012345678901234"));
        assertTrue(toString.contains("birth=" + birthDate.toString()));
        assertTrue(toString.contains("phone=+212600000000"));
    }
}
