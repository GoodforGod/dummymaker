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

    public static Object generateObject(final IGenerator generator,
                                        final Class<?> castType,
                                        final Class<?> fieldType) {
        return castObject(generator.generate(), castType, fieldType);
    }

    public static Object generateObject(final IGenerator generator,
                                        final Class<?> fieldType,
                                        final boolean isTypeAssignable,
                                        final boolean isTypeEquals,
                                        final boolean isTypeObject,
                                        final boolean isTypeString) {
        return castObject(generator.generate(), fieldType,
                isTypeAssignable, isTypeEquals,
                isTypeObject, isTypeString);
    }

    public static Object castObject(final Object castObject,
                                    final Class<?> castType,
                                    final Class<?> fieldType) {
        final boolean isTypeAssignable = fieldType.isAssignableFrom(castType);
        final boolean isTypeEquals = castType.equals(fieldType);
        final boolean isTypeObject = fieldType.equals(Object.class);
        final boolean isTypeString = fieldType.equals(String.class);

        return castObject(castObject, fieldType,
                isTypeAssignable, isTypeEquals,
                isTypeObject, isTypeString);
    }

    public static Object castObject(final Object castObject,
                                    final Class<?> fieldType,
                                    final boolean isTypeAssignable,
                                    final boolean isTypeEquals,
                                    final boolean isTypeObject,
                                    final boolean isTypeString) {
        if (isTypeEquals || isTypeObject) {
            return (castObject);
        } else if (isTypeString) {
            return (String.valueOf(castObject));
        } else if (isTypeAssignable) {
            return (fieldType.cast(castObject));
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
