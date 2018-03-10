package io.dummymaker.util;

import io.dummymaker.generator.IGenerator;

import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Logger;

/**
 * "default comment"
 *
 * @author GoodforGod
 * @since 06.03.2018
 */
public class BasicCastUtils {

    public static Logger logger = Logger.getLogger(BasicCastUtils.class.getName());

    public static final Object EMPTY = new Object();

    public static <T> T instanceClass(final Class<T> tClass) {
        try {
            return tClass.newInstance();
        } catch (InstantiationException e) {
            logger.warning(e.getMessage() + " | " + tClass + " | CAN NOT INSTANTIATE, NO ZERO PUBLIC CONSTRUCTOR.");
        } catch (IllegalAccessException e) {
            logger.warning(e.getMessage() + " | " + tClass + " | NO ACCESS TO INSTANTIATING OBJECT.");
        }
        return null;
    }

    /**
     * Try to generate and cast object to field type or string if possible
     *
     * @param generator generator
     * @param fieldType actual field type
     * @return generated and casted object
     */
    public static Object generateObject(final IGenerator generator,
                                        final Class<?> fieldType) {
        if(generator == null)
            return EMPTY;

        final Object object = generator.generate();
        if(object == null)
            return EMPTY;

        return castObject(object, fieldType);
    }

    /**
     * Try to generate and cast object to field type or string if possible
     *
     * @param castObject cast object
     * @param fieldType actual field type
     * @return generated and casted object
     */
    public static Object castObject(final Object castObject,
                                    final Class<?> fieldType) {
        if(fieldType == null)
            return EMPTY;

        final Class<?> castType = castObject.getClass();
        final boolean isTypeAssignable = fieldType.isAssignableFrom(castType);
        final boolean isTypeEquals = castType.equals(fieldType);
        final boolean isTypeObject = fieldType.equals(Object.class);
        final boolean isTypeString = fieldType.equals(String.class);

        return castObject(castObject, fieldType,
                isTypeAssignable, isTypeEquals,
                isTypeObject, isTypeString);
    }

    private static Object castObject(final Object castObject,
                                    final Class<?> fieldType,
                                    final boolean isTypeAssignable,
                                    final boolean isTypeEquals,
                                    final boolean isTypeObject,
                                    final boolean isTypeString) {
        if (isTypeEquals || isTypeObject) {
            return castObject;
        } else if (isTypeString) {
            return String.valueOf(castObject);
        } else if (isTypeAssignable) {
            return fieldType.cast(castObject);
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
