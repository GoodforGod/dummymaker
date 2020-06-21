package io.dummymaker.util;

import io.dummymaker.model.Pair;
import org.junit.Assert;
import org.junit.Test;

/**
 * Description in progress
 *
 * @author Anton Kurako (GoodforGod)
 * @since 5.5.2020
 */
public class PairTests extends Assert {

    @Test
    public void anyEmptyFalse() {
        final Pair<String, String> pair = Pair.of("1", "2");
        assertFalse(pair.anyEmpty());
    }

    @Test
    public void allEmptyFalse() {
        final Pair<String, String> pair = Pair.of("1", "2");
        assertFalse(pair.allEmpty());
    }

    @Test
    public void anyEmptyTrue() {
        final Pair<String, String> pair = Pair.of(null, "2");
        assertTrue(pair.anyEmpty());

        final Pair<String, String> pair2 = Pair.of("1", null);
        assertTrue(pair2.anyEmpty());

        assertFalse(pair.allEmpty());
        assertFalse(pair2.allEmpty());

        assertNotEquals(pair, pair2);
    }

    @Test
    public void allEmptyTrue() {
        final Pair<String, String> pair = Pair.of(null, null);
        assertTrue(pair.allEmpty());
    }

    @Test
    public void equalTrue() {
        final Pair<String, String> pair1 = Pair.of("1", "2");
        final Pair<String, String> pair2 = Pair.of("1", "2");
        assertEquals(pair1, pair2);
    }

    @Test
    public void notNullFirstOrSecond() {
        final Pair<String, String> pair = Pair.of(null, null);
        assertTrue(pair.allEmpty());
        assertTrue(pair.anyEmpty());

        assertNotNull(pair.first());
        assertFalse(pair.first().isPresent());

        assertNotNull(pair.second());
        assertFalse(pair.second().isPresent());
    }

    @Test
    public void emptyIsEmpty() {
        final Pair<Object, Object> pair = Pair.empty();
        assertTrue(pair.allEmpty());
        assertTrue(pair.anyEmpty());

        assertNotNull(pair.first());
        assertFalse(pair.first().isPresent());

        assertNotNull(pair.second());
        assertFalse(pair.second().isPresent());
    }
}
