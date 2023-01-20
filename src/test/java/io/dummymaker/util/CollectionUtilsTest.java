package io.dummymaker.util;

import java.util.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author GoodforGod
 * @since 11.03.2018
 */
class CollectionUtilsTest extends Assertions {

    @Test
    void isEmptyCollectionEmpty() {
        Collection<Object> collection = new ArrayList<>();
        assertTrue(CollectionUtils.isEmpty(collection));
    }

    @Test
    void isEmptyCollectionNull() {
        assertTrue(CollectionUtils.isEmpty((Collection<?>) null));
    }

    @Test
    void isNotEmptyNotCollection() {
        Collection<Object> collection = new ArrayList<>();
        collection.add(new Object());
        assertTrue(CollectionUtils.isNotEmpty(collection));
    }

    @Test
    void isNotEmptyNotForEmptyCollection() {
        assertFalse(CollectionUtils.isNotEmpty((Map<?, ?>) null));
    }

    @Test
    void isEmptyMapEmpty() {
        Map<?, ?> map = new HashMap<>();
        assertTrue(CollectionUtils.isEmpty(map));
    }

    @Test
    void isEmptyMapNull() {
        assertTrue(CollectionUtils.isEmpty((Map<?, ?>) null));
    }

    @Test
    void isNotEmptyNotMap() {
        Map<String, String> map = new HashMap<>();
        map.put("s", "s");
        assertTrue(CollectionUtils.isNotEmpty(map));
        assertFalse(CollectionUtils.isEmpty(map));
    }

    @Test
    void isNotEmptyNotForEmptyMap() {
        assertFalse(CollectionUtils.isNotEmpty((Map<?, ?>) null));
    }

    @Test
    void isEmptyArray() {
        String[] arr = new String[0];
        assertTrue(CollectionUtils.isEmpty(arr));
    }

    @Test
    void isEmptyArrayWhenNull() {
        assertTrue(CollectionUtils.isEmpty((Object[]) null));
    }

    @Test
    void isNonEmptyArray() {
        String[] arr = new String[] { "my" };
        assertFalse(CollectionUtils.isEmpty(arr));
    }

    @Test
    void getRandomFromArray() {
        String[] arr = new String[] { "my" };
        String random = CollectionUtils.random(arr);
        assertEquals("my", random);
    }

    @Test
    void getRandomFromCollection() {
        List<String> list = Arrays.asList("my");
        String random = CollectionUtils.random(list);
        assertEquals("my", random);
    }

    @Test
    void getRandomFromCollectionOfTwo() {
        List<String> list = Arrays.asList("my", "his");
        String random = CollectionUtils.random(list);
        assertTrue("my".equals(random) || "his".equals(random));
    }
}
