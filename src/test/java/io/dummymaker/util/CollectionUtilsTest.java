package io.dummymaker.util;


import java.util.*;
import org.junit.Assert;
import org.junit.Test;


/**
 * "default comment"
 *
 * @author GoodforGod
 * @since 11.03.2018
 */
public class CollectionUtilsTest extends Assert {

    @Test
    public void isEmptyCollectionEmpty() {
        Collection<Object> collection = new ArrayList<>();
        assertTrue(CollectionUtils.isEmpty(collection));
    }

    @Test
    public void isEmptyCollectionNull() {
        assertTrue(CollectionUtils.isEmpty((Collection<?>) null));
    }

    @Test
    public void isNotEmptyNotCollection() {
        Collection<Object> collection = new ArrayList<>();
        collection.add(new Object());
        assertTrue(CollectionUtils.isNotEmpty(collection));
    }

    @Test
    public void isNotEmptyNotForEmptyCollection() {
        assertFalse(CollectionUtils.isNotEmpty((Map<?, ?>) null));
    }

    @Test
    public void isEmptyMapEmpty() {
        Map<?, ?> map = new HashMap<>();
        assertTrue(CollectionUtils.isEmpty(map));
    }

    @Test
    public void isEmptyMapNull() {
        assertTrue(CollectionUtils.isEmpty((Map<?, ?>) null));
    }

    @Test
    public void isNotEmptyNotMap() {
        Map<String, String> map = new HashMap<>();
        map.put("s", "s");
        assertTrue(CollectionUtils.isNotEmpty(map));
        assertFalse(CollectionUtils.isEmpty(map));
    }

    @Test
    public void isNotEmptyNotForEmptyMap() {
        assertFalse(CollectionUtils.isNotEmpty((Map<?, ?>) null));
    }

    @Test
    public void isEmptyArray() {
        String[] arr = new String[0];
        assertTrue(CollectionUtils.isEmpty(arr));
    }

    @Test
    public void isEmptyArrayWhenNull() {
        assertTrue(CollectionUtils.isEmpty((Object[]) null));
    }

    @Test
    public void isNonEmptyArray() {
        String[] arr = new String[] { "my" };
        assertFalse(CollectionUtils.isEmpty(arr));
    }

    @Test
    public void getRandomFromArray() {
        String[] arr = new String[] { "my" };
        String random = CollectionUtils.random(arr);
        assertEquals("my", random);
    }

    @Test
    public void getRandomFromCollection() {
        List<String> list = Arrays.asList("my");
        String random = CollectionUtils.random(list);
        assertEquals("my", random);
    }

    @Test
    public void getRandomFromCollectionOfTwo() {
        List<String> list = Arrays.asList("my", "his");
        String random = CollectionUtils.random(list);
        assertTrue("my".equals(random) || "his".equals(random));
    }
}
