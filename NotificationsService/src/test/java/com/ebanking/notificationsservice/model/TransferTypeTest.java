package com.ebanking.notificationsservice.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TransferTypeTest {

    @Test
    void testEnumValues() {
        // Act
        TransferType[] types = TransferType.values();

        // Assert
        assertEquals(7, types.length);
        assertEquals(TransferType.WALLET_TO_WALLET, types[0]);
        assertEquals(TransferType.WALLET_TO_GAB, types[1]);
        assertEquals(TransferType.GAB_TO_BANK, types[2]);
        assertEquals(TransferType.WALLET_TO_BANK, types[3]);
        assertEquals(TransferType.BANK_TO_BANK, types[4]);
        assertEquals(TransferType.BANK_TO_GAB, types[5]);
        assertEquals(TransferType.MULTIPLE, types[6]);
    }

    @Test
    void testEnumValueOf() {
        // Act & Assert
        assertEquals(TransferType.WALLET_TO_WALLET, TransferType.valueOf("WALLET_TO_WALLET"));
        assertEquals(TransferType.WALLET_TO_GAB, TransferType.valueOf("WALLET_TO_GAB"));
        assertEquals(TransferType.GAB_TO_BANK, TransferType.valueOf("GAB_TO_BANK"));
        assertEquals(TransferType.WALLET_TO_BANK, TransferType.valueOf("WALLET_TO_BANK"));
        assertEquals(TransferType.BANK_TO_BANK, TransferType.valueOf("BANK_TO_BANK"));
        assertEquals(TransferType.BANK_TO_GAB, TransferType.valueOf("BANK_TO_GAB"));
        assertEquals(TransferType.MULTIPLE, TransferType.valueOf("MULTIPLE"));
    }

    @Test
    void testInvalidEnumValue() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> TransferType.valueOf("INVALID_TYPE"));
    }
}
