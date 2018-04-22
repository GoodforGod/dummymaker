package io.dummymaker.util;

import io.dummymaker.generator.simple.IGenerator;

import java.lang.reflect.*;
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

    public static Object castToNumber(final Object value,
                                      final Class<?> fieldType) {
        if (fieldType.isAssignableFrom(Integer.class)) {
            return Integer.valueOf(String.valueOf(value));
        } else if (fieldType.isAssignableFrom(Long.class)) {
            return Long.valueOf(String.valueOf(value));
        } else if(fieldType.isAssignableFrom(Double.class)) {
            return Double.valueOf(String.valueOf(value));
        }
        return null;
    }

    /**
     * Instantiate class if possible, or return default provided value
     * @param tClass class to instantiate
     * @param defaultEntity def provided value
     * @param <T> class instance
     * @return class instance
     */
    public static <T> T instantiate(final Class<T> tClass,
                                    final T defaultEntity) {
        final T t = instantiate(tClass);
        return (t == null)
                ? defaultEntity
                : t;
    }

    /**
     * Instantiate class if possible, or return default provided value
     * @param tClass class to instantiate
     * @param <T> class instance
     * @return class instance
     */
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
     * Extracts field generic type from generic type
     *
     * @param field to extract from
     * @return actual type
     */
    public static Type getGenericType(final Field field) {
        return getGenericType(field, 0);
    }

    /**
     * Extracts field generic type from generic type
     *
     * @param field to extract from
     * @param paramNumber actual param number in parameterized type array
     * @return actual type
     */
    public static Type getGenericType(final Field field,
                                      final int paramNumber) {
        final ParameterizedType parameterizedType = ((ParameterizedType) field.getGenericType());
        return (parameterizedType.getActualTypeArguments().length < paramNumber)
                ? Object.class
                : parameterizedType.getActualTypeArguments()[paramNumber];
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
