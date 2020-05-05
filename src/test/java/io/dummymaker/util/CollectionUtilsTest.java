package io.dummymaker.util;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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
}
