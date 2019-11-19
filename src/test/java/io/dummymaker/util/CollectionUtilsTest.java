package io.dummymaker.util;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

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
        assertTrue(CollectionUtils.isEmpty(null));
    }

    @Test
    public void isNotEmptyNotCollection() {
        Collection<Object> collection = new ArrayList<>();
        collection.add(new Object());
        assertTrue(CollectionUtils.isNotEmpty(collection));
    }

    @Test
    public void isNotEmptyNotForEmptyCollection() {
        assertFalse(CollectionUtils.isNotEmpty(null));
    }
}
