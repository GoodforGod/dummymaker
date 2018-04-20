package io.dummymaker.util;

import io.dummymaker.generator.IGenerator;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.logging.Logger;

/**
 * "default comment"
 *
 * @author GoodforGod
 * @since 06.03.2018
 */
public class BasicCastUtils {

    public static final Logger logger = Logger.getLogger(BasicCastUtils.class.getName());

    public static final Object EMPTY = new Object();

    @SuppressWarnings("unchecked")
    public static <T> T instanceClass(final Class<T> tClass) {
        try {
            if(tClass == null)
                return null;

            final Constructor zeroArgConstructor = Arrays.stream(tClass.getDeclaredConstructors())
                    .filter(c -> c.getParameterCount() == 0)
                    .findFirst().orElse(null);

            if(zeroArgConstructor == null) {
                logger.warning("[CAN NOT INSTANTIATE] : " + tClass + ", have NO zero arg constructor.");
                return null;
            }

            return (T) zeroArgConstructor.newInstance();
        } catch (InstantiationException | InvocationTargetException e) {
            logger.warning("[CAN NOT INSTANTIATE] : " + tClass
                    + ", class may be an abstract class, an interface, "
                    + "array, primitive." + e.getMessage());
        } catch (IllegalAccessException e) {
            logger.warning("[CAN NOT INSTANTIATE] : " + tClass
                    + ", no access to instantiating object." + e.getMessage());
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

        final boolean isTypeString = fieldType.equals(String.class);
        if(castObject == null) {
            return (isTypeString)
                    ? "null"
                    : EMPTY;
        }

        final Class<?> castType = castObject.getClass();
        final boolean isTypeAssignable = fieldType.isAssignableFrom(castType);
        final boolean isTypeEquals = castType.equals(fieldType);
        final boolean isTypeObject = fieldType.equals(Object.class);

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
}
