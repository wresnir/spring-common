package dev.wresni.common.utilities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BooleanUtilTest {

    @Test
    void of() {
        assertTrue(BooleanUtil.of(true));
        assertFalse(BooleanUtil.of(false));
        assertFalse(BooleanUtil.of((Boolean) null));

        assertTrue(BooleanUtil.of(-1));
        assertFalse(BooleanUtil.of(0));
        assertFalse(BooleanUtil.of(Integer.MAX_VALUE));
        assertFalse(BooleanUtil.of((Number) null));
    }

    @Test
    void ngeatedOf() {
        assertFalse(BooleanUtil.negatedOf(true));
        assertTrue(BooleanUtil.negatedOf(false));
        assertTrue(BooleanUtil.negatedOf((Boolean) null));

        assertFalse(BooleanUtil.negatedOf(-1));
        assertTrue(BooleanUtil.negatedOf(0));
        assertTrue(BooleanUtil.negatedOf(Integer.MAX_VALUE));
        assertTrue(BooleanUtil.negatedOf((Number) null));
    }

    @Test
    void toActive() {
        assertEquals("Active", BooleanUtil.toActive(true));
        assertEquals("Inactive", BooleanUtil.toActive(false));
        assertEquals("Inactive", BooleanUtil.toActive(null));
    }
}