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
public class BasicCollectionUtilsTest extends Assert {

    @Test
    public void isEmptyCollectionEmpty() {
        Collection<Object> collection = new ArrayList<>();
        assertTrue(BasicCollectionUtils.isEmpty(collection));
    }

    @Test
    public void isEmptyCollectionNull() {
        assertTrue(BasicCollectionUtils.isEmpty(null));
    }

    @Test
    public void isNotEmptyNotCollection() {
        Collection<Object> collection = new ArrayList<>();
        collection.add(new Object());
        assertTrue(BasicCollectionUtils.isNotEmpty(collection));
    }

    @Test
    public void isNotEmptyNotForEmptyCollection() {
        assertFalse(BasicCollectionUtils.isNotEmpty(null));
    }
}
