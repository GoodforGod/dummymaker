package io.dummymaker.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * "default comment"
 *
 * @author GoodforGod
 * @since 27.02.2018
 */
public class BasicStringUtilsTest extends Assert {

    @Test
    public void nullStringIsEmpty() {
        assertTrue(BasicStringUtils.isEmpty(null));
    }

    @Test
    public void emptyStringIsEmpty() {
        assertTrue(BasicStringUtils.isEmpty(""));
    }
    @Test
    public void nonEmptyStringIsNonEmpty() {
        assertFalse(BasicStringUtils.isEmpty("Bob"));
    }

    @Test
    public void nullStringIsBlank() {
        assertTrue(BasicStringUtils.isBlank(null));
    }

    @Test
    public void emptySpaceStringIsBlank() {
        assertTrue(BasicStringUtils.isBlank("        "));
    }

    @Test
    public void nonBlankStringIsNonBlank() {
        assertTrue(BasicStringUtils.isNotBlank("  Bob "));
    }

    @Test
    public void blankStringIsNonBlank() {
        assertFalse(BasicStringUtils.isNotBlank("    "));
    }
}
