package io.dummymaker.util;

import io.dummymaker.generator.simple.impl.NullGenerator;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;

/**
 * "default comment"
 *
 * @author GoodforGod
 * @since 11.03.2018
 */
public class BasicCastUtilsTest extends Assert {

    @Test
    public void generateAmountMinMoreMax() {
        int amount = BasicCollectionUtils.generateRandomAmount(10, 1);
        assertEquals(10, amount);
    }

    @Test
    public void generateAmountMinLessOne() {
        int amount = BasicCollectionUtils.generateRandomAmount(-10, 0);
        assertEquals(0, amount);
    }

    @Test
    public void generateAmountMaxLessOne() {
        int amount = BasicCollectionUtils.generateRandomAmount(1, -1);
        assertEquals(1, amount);
    }

    @Test
    public void castNullFieldType() {
        Object object = BasicCastUtils.castObject(new Object(), null);
        assertNull(object);
    }

    @Test
    public void castNullGenerator() {
        Object object = BasicCastUtils.generateObject(null, LocalDate.class);
        assertNull(object);
    }

    @Test
    public void castNullableGenerator() {
        Object object = BasicCastUtils.generateObject(new NullGenerator(), Long.class);
        assertNull(object);
    }
}
