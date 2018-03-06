package io.dummymaker.util;

import io.dummymaker.generator.IGenerator;

import java.util.concurrent.ThreadLocalRandom;

/**
 * "default comment"
 *
 * @author GoodforGod
 * @since 06.03.2018
 */
public class BasicCastUtils {

    public static final Object EMPTY = new Object();

    private BasicCastUtils() { }

    public static Object generateObject(final IGenerator generator,
                                        final Class<?> fieldType,
                                        final boolean isTypeAssignable,
                                        final boolean isTypeEquals,
                                        final boolean isTypeObject,
                                        final boolean isTypeString) {
        if (isTypeEquals || isTypeObject) {
            return (generator.generate());
        } else if (isTypeString) {
            return (String.valueOf(generator.generate()));
        } else if (isTypeAssignable) {
            return (fieldType.cast(generator.generate()));
        }
        return EMPTY;
    }

    public static int generateRandomAmount(final int min,
                                           final int max) {
        final int usedMin = (min < 1) ? 1 : min;
        final int usedMax = (max < 1) ? 1 : max;

        return (usedMin >= usedMax)
                ? usedMin
                : ThreadLocalRandom.current().nextInt(min, max);
    }
}
