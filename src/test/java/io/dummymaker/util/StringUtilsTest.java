package io.dummymaker.util;


import org.junit.Assert;
import org.junit.Test;


/**
 * "default comment"
 *
 * @author GoodforGod
 * @since 27.02.2018
 */
public class StringUtilsTest extends Assert {

    @Test
    public void nullStringIsEmpty() {
        assertTrue(StringUtils.isEmpty(null));
    }

    @Test
    public void emptyStringIsEmpty() {
        assertTrue(StringUtils.isEmpty(""));
    }

    @Test
    public void nonEmptyStringIsNonEmpty() {
        assertFalse(StringUtils.isEmpty("Bob"));
    }

    @Test
    public void nullStringIsBlank() {
        assertTrue(StringUtils.isBlank(null));
    }

    @Test
    public void emptySpaceStringIsBlank() {
        assertTrue(StringUtils.isBlank("        "));
    }

    @Test
    public void nonBlankStringIsNonBlank() {
        assertTrue(StringUtils.isNotBlank("  Bob "));
    }

    @Test
    public void blankStringIsNonBlank() {
        assertFalse(StringUtils.isNotBlank("    "));
    }
}
