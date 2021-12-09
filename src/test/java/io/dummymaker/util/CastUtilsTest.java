package io.dummymaker.util;


import io.dummymaker.generator.simple.NullGenerator;
import java.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;


/**
 * "default comment"
 *
 * @author GoodforGod
 * @since 11.03.2018
 */
public class CastUtilsTest extends Assert {

    @Test
    public void generateAmountMinMoreMax() {
        int amount = CollectionUtils.random(10, 1);
        assertEquals(10, amount);
    }

    @Test
    public void generateAmountMinLessOne() {
        int amount = CollectionUtils.random(-10, 0);
        assertEquals(0, amount);
    }

    @Test
    public void generateAmountMaxLessOne() {
        int amount = CollectionUtils.random(1, -1);
        assertEquals(1, amount);
    }

    @Test
    public void castNullFieldType() {
        Object object = CastUtils.castObject(new Object(), null);
        assertNull(object);
    }

    @Test
    public void castNullGenerator() {
        Object object = CastUtils.generateObject(null, LocalDate.class);
        assertNull(object);
    }

    @Test
    public void castNullableGenerator() {
        Object object = CastUtils.generateObject(new NullGenerator(), Long.class);
        assertNull(object);
    }
}
