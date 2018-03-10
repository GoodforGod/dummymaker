package io.dummymaker.util;

import io.dummymaker.generator.impl.NullGenerator;
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
    private BasicCastUtils utils = new BasicCastUtils();

    @Test
    public void generateAmountMinMoreMax() {
        int amount = BasicCastUtils.generateRandomAmount(10, 1);
        assertEquals(10, amount);
    }

    @Test
    public void generateAmountMinLessOne() {
        int amount = BasicCastUtils.generateRandomAmount(-10, 1);
        assertEquals(1, amount);
    }

    @Test
    public void generateAmountMaxLessOne() {
        int amount = BasicCastUtils.generateRandomAmount(1, -1);
        assertEquals(1, amount);
    }

    @Test
    public void castNullFieldType() {
        Object object = BasicCastUtils.castObject(new Object(), null);
        assertEquals(BasicCastUtils.EMPTY, object);
    }

    @Test
    public void castNullGenerator() {
        Object object = BasicCastUtils.generateObject(null, LocalDate.class);
        assertEquals(BasicCastUtils.EMPTY, object);
    }

    @Test
    public void castNullableGenerator() {
        Object object = BasicCastUtils.generateObject(new NullGenerator(), Long.class);
        assertEquals(BasicCastUtils.EMPTY, object);
    }
}
