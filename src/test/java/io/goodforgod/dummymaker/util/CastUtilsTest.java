package io.goodforgod.dummymaker.util;

import io.goodforgod.dummymaker.generator.simple.NullGenerator;
import java.time.LocalDate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author GoodforGod
 * @since 11.03.2018
 */
class CastUtilsTest extends Assertions {

    @Test
    void generateAmountMinMoreMax() {
        int amount = RandomUtils.random(10, 1);
        assertEquals(10, amount);
    }

    @Test
    void generateAmountMinLessOne() {
        int amount = RandomUtils.random(-10, 0);
        assertEquals(0, amount);
    }

    @Test
    void generateAmountMaxLessOne() {
        int amount = RandomUtils.random(1, -1);
        assertEquals(1, amount);
    }

    @Test
    void castNullFieldType() {
        Object object = CastUtils.castObject(new Object(), null);
        assertNull(object);
    }

    @Test
    void castNullGenerator() {
        Object object = CastUtils.generateObject(null, LocalDate.class);
        assertNull(object);
    }

    @Test
    void castNullableGenerator() {
        Object object = CastUtils.generateObject(new NullGenerator(), Long.class);
        assertNull(object);
    }
}
