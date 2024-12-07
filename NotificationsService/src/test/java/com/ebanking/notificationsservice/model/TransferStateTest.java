package com.ebanking.notificationsservice.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TransferStateTest {

    @Test
    void testEnumValues() {
        // Act
        TransferState[] states = TransferState.values();

        // Assert
        assertEquals(7, states.length);
        assertEquals(TransferState.TO_BE_SERVED, states[0]);
        assertEquals(TransferState.SERVED, states[1]);
        assertEquals(TransferState.EXTOURNED, states[2]);
        assertEquals(TransferState.RESET, states[3]);
        assertEquals(TransferState.BLOCKED, states[4]);
        assertEquals(TransferState.UNBLOCKED, states[5]);
        assertEquals(TransferState.ESCHEAT, states[6]);
    }

    @Test
    void testEnumValueOf() {
        // Act & Assert
        assertEquals(TransferState.TO_BE_SERVED, TransferState.valueOf("TO_BE_SERVED"));
        assertEquals(TransferState.SERVED, TransferState.valueOf("SERVED"));
        assertEquals(TransferState.EXTOURNED, TransferState.valueOf("EXTOURNED"));
        assertEquals(TransferState.RESET, TransferState.valueOf("RESET"));
        assertEquals(TransferState.BLOCKED, TransferState.valueOf("BLOCKED"));
        assertEquals(TransferState.UNBLOCKED, TransferState.valueOf("UNBLOCKED"));
        assertEquals(TransferState.ESCHEAT, TransferState.valueOf("ESCHEAT"));
    }

    @Test
    void testInvalidEnumValue() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> TransferState.valueOf("INVALID_STATE"));
    }
}
