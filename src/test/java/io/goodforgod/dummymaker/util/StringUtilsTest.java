package io.goodforgod.dummymaker.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author GoodforGod
 * @since 27.02.2018
 */
class StringUtilsTest extends Assertions {

    @Test
    void nullStringIsEmpty() {
        assertTrue(StringUtils.isEmpty(null));
    }

    @Test
    void emptyStringIsEmpty() {
        assertTrue(StringUtils.isEmpty(""));
    }

    @Test
    void nonEmptyStringIsNonEmpty() {
        assertFalse(StringUtils.isEmpty("Bob"));
    }

    @Test
    void nullStringIsBlank() {
        assertTrue(StringUtils.isBlank(null));
    }

    @Test
    void emptySpaceStringIsBlank() {
        assertTrue(StringUtils.isBlank("        "));
    }

    @Test
    void nonBlankStringIsNonBlank() {
        assertTrue(StringUtils.isNotBlank("  Bob "));
    }

    @Test
    void blankStringIsNonBlank() {
        assertFalse(StringUtils.isNotBlank("    "));
    }
}
