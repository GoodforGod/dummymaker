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

    private static final Logger logger = Logger.getLogger(BasicCastUtils.class.getName());

    public static final Object UNKNOWN = new Object();

    public static <T> T instantiate(final Class<T> tClass, T defaultEntity) {
        final T t = instantiate(tClass);
        return (t == null)
                ? defaultEntity
                : t;
    }

    @SuppressWarnings("unchecked")
    public static <T> T instantiate(final Class<T> tClass) {
        try {
            if(tClass == null)
                return null;

            final Constructor<?> zeroArgConstructor = Arrays.stream(tClass.getDeclaredConstructors())
                    .filter(c -> c.getParameterCount() == 0)
                    .findFirst().orElse(null);

            if(zeroArgConstructor == null) {
                logger.warning("[CAN NOT INSTANTIATE] : " + tClass + ", have NO zero arg constructor.");
                return null;
            }

            return ((T) zeroArgConstructor.newInstance());
        } catch (InstantiationException | InvocationTargetException e) {
            logger.warning("[CAN NOT INSTANTIATE] : " + tClass
                    + " - class may be an abstract class, an interface, "
                    + "array, primitive." + e.getMessage());
        } catch (IllegalAccessException e) {
            logger.warning("[CAN NOT INSTANTIATE] : " + tClass
                    + " - no access to instantiating object." + e.getMessage());
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
    @SuppressWarnings("unchecked")
    public static <T> T generateObject(final IGenerator generator,
                                        final Class<T> fieldType) {
        if(generator == null)
            return null;

        final Object object = generator.generate();
        return castObject(object, fieldType);
    }

    /**
     * Try to generate and cast object to field type or string if possible
     *
     * @param castObject cast object
     * @param fieldType actual field type
     * @return generated and casted object
     */
    @SuppressWarnings("unchecked")
    public static <T> T castObject(final Object castObject,
                                   final Class<T> fieldType) {
        if(fieldType == null)
            return null;

        final boolean isTypeString = fieldType.equals(String.class);
        if(castObject == null) {
            return (isTypeString)
                    ? ((T) "null")
                    : null;
        }

        final Class<?> castType         = castObject.getClass();
        final boolean isTypeAssignable  = fieldType.isAssignableFrom(castType);
        final boolean isTypeEquals      = castType.equals(fieldType);
        final boolean isTypeObject      = fieldType.equals(Object.class);

        return castObject(castObject, fieldType,
                isTypeAssignable, isTypeEquals,
                isTypeObject, isTypeString);
    }

    @SuppressWarnings("unchecked")
    private static <T> T castObject(final Object castObject,
                                    final Class<T> fieldType,
                                    final boolean isTypeAssignable,
                                    final boolean isTypeEquals,
                                    final boolean isTypeObject,
                                    final boolean isTypeString) {
        if (isTypeEquals || isTypeObject) {
            return ((T) castObject);
        } else if (isTypeString) {
            return ((T) String.valueOf(castObject));
        } else if (isTypeAssignable) {
            return fieldType.cast(castObject);
        }
        return null;
    }
}
