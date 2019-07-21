package io.dummymaker.util;

import io.dummymaker.generator.simple.IGenerator;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.logging.Logger;

/**
 * Utils for object casting
 *
 * @author GoodforGod
 * @since 06.03.2018
 */
public class BasicCastUtils {

    private static final Logger logger = Logger.getLogger(BasicCastUtils.class.getName());

    public static Object castToNumber(final Object value, final Class<?> fieldType) {
        switch (CastType.of(fieldType)) {
            case BYTE:
                return Byte.valueOf(String.valueOf(value));
            case CHAR:
                return String.valueOf(value).charAt(0);
            case SHORT:
                return Short.valueOf(String.valueOf(value));
            case BOOLEAN:
                return Boolean.valueOf(String.valueOf(value));
            case INT:
                return Integer.valueOf(String.valueOf(value));
            case LONG:
                return Long.valueOf(String.valueOf(value));
            case FLOAT:
                return Float.valueOf(String.valueOf(value));
            case DOUBLE:
                return Double.valueOf(String.valueOf(value));
            case BIG_INT:
                return BigInteger.valueOf(Long.valueOf(String.valueOf(value)));
            case BIG_DECIMAL:
                return BigDecimal.valueOf(Long.valueOf(String.valueOf(value)));
            default:
                return value;
        }
    }

    /**
     * Instantiate class if possible, or return default provided value
     *
     * @param tClass        class to instantiate
     * @param defaultEntity def provided value
     * @param <T>           class instance
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
     *
     * @param tClass class to instantiate
     * @param <T>    class instance
     * @return class instance
     */
    @SuppressWarnings("unchecked")
    public static <T> T instantiate(final Class<T> tClass) {
        if (tClass == null)
            return null;

        final Constructor<?> zeroArgConstructor = Arrays.stream(tClass.getDeclaredConstructors())
                .filter(c -> c.getParameterCount() == 0)
                .findFirst().orElse(null);

        try {
            if (zeroArgConstructor == null) {
                logger.warning("[CAN NOT INSTANTIATE] : " + tClass + ", have NO zero arg constructor.");
                return null;
            }
            zeroArgConstructor.setAccessible(true);
            return ((T) zeroArgConstructor.newInstance());
        } catch (InstantiationException | InvocationTargetException e) {
            logger.warning("[CAN NOT INSTANTIATE] : " + tClass
                    + " - class may be an abstract class, an interface, "
                    + "array, primitive." + e.getMessage());
            return null;
        } catch (IllegalAccessException e) {
            logger.warning("[CAN NOT INSTANTIATE] : " + tClass
                    + " - no access to instantiating object." + e.getMessage());
            return null;
        } finally {
            if (zeroArgConstructor != null)
                zeroArgConstructor.setAccessible(false);
        }
    }

    /**
     * Try to generate and cast object to field type or string if possible
     *
     * @param generator generator
     * @param fieldType actual field type
     * @param <T>       generic type
     * @return generated and casted object
     */
    public static <T> T generateObject(final IGenerator generator,
                                       final Class<T> fieldType) {
        if (generator == null)
            return null;

        final Object object = generator.generate();
        return castObject(object, fieldType);
    }

    /**
     * Extracts generic type
     *
     * @param type to extract from
     * @return actual type or object if length is not presented
     */
    public static Type getGenericType(final Type type) {
        return getGenericType(type, 0);
    }

    /**
     * Extracts generic type
     *
     * @param type        to extract from
     * @param paramNumber actual param number in parameterized type array
     * @return actual type or object if length is not presented
     */
    public static Type getGenericType(final Type type,
                                      final int paramNumber) {
        try {
            final ParameterizedType parameterizedType = ((ParameterizedType) type);
            return (parameterizedType.getActualTypeArguments().length < paramNumber)
                    ? Object.class
                    : parameterizedType.getActualTypeArguments()[paramNumber];
        } catch (Exception e) {
            return Object.class;
        }
    }

    /**
     * Try to generate and cast object to field type or string if possible
     *
     * @param castObject cast object
     * @param fieldType  actual field type
     * @param <T>        generic type
     * @return generated and casted object
     */
    @SuppressWarnings("unchecked")
    public static <T> T castObject(final Object castObject,
                                   final Class<T> fieldType) {
        if (fieldType == null)
            return null;

        final boolean isTypeString = fieldType.equals(String.class);
        if (castObject == null) {
            return (isTypeString)
                    ? ((T) "null")
                    : null;
        }

        final Class<?> castType = castObject.getClass();
        final boolean isTypeAssignable = fieldType.isAssignableFrom(castType);
        final boolean isTypeEquals = areEquals(castType, fieldType);
        final boolean isTypeObject = fieldType.equals(Object.class);

        final Object boxedIfPossible = boxObject(castObject, fieldType);

        return castObject(boxedIfPossible, fieldType,
                isTypeAssignable, isTypeEquals,
                isTypeObject, isTypeString);
    }

    /**
     * Try to box downcast or box object if it is boxed primitive type
     * And field is also boxed primitive (can't cast one to another explicitly)
     */
    private static <T> Object boxObject(final Object castObject,
                                        final Class<T> fieldType) {
        final CastType firstType = CastType.of(castObject.getClass());
        final CastType secondType = CastType.of(fieldType);

        return (secondType.equals(CastType.UNKNOWN) || firstType.equals(CastType.UNKNOWN))
                ? castObject
                : castToNumber(castObject, fieldType);
    }

    /**
     * Check if objects have equals types, even if they are primitive
     */
    private static boolean areEquals(final Class<?> firstClass,
                                     final Class<?> secondClass) {
        final CastType firstType = CastType.of(firstClass);
        final CastType secondType = CastType.of(secondClass);

        return (firstType.equals(CastType.UNKNOWN) || secondType.equals(CastType.UNKNOWN))
                ? firstClass.equals(secondClass)
                : firstType.equals(secondType);
    }

    @SuppressWarnings("unchecked")
    private static <T> T castObject(final Object castObject,
                                    final Class<T> fieldType,
                                    final boolean isTypeAssignable,
                                    final boolean isTypeEquals,
                                    final boolean isTypeObject,
                                    final boolean isTypeString) {
        if (isTypeEquals || isTypeObject)
            return ((T) castObject);
        else if (isTypeString)
            return ((T) String.valueOf(castObject));
        else if (isTypeAssignable)
            return fieldType.cast(castObject);

        // Try to force cast anyway (need more testing)
        return ((T) castObject);
    }

    private enum CastType {
        UNKNOWN,
        STRING,
        BOOLEAN,
        BYTE,
        SHORT,
        CHAR,
        INT,
        LONG,
        FLOAT,
        DOUBLE,
        BIG_INT,
        BIG_DECIMAL;

        public static CastType of(final Class<?> type) {
            if (type.isAssignableFrom(String.class)) {
                return STRING;
            } else if (type.isAssignableFrom(Boolean.class) || type.isAssignableFrom(boolean.class)) {
                return BOOLEAN;
            } else if (type.isAssignableFrom(Byte.class) || type.isAssignableFrom(byte.class)) {
                return BYTE;
            } else if (type.isAssignableFrom(Short.class) || type.isAssignableFrom(short.class)) {
                return SHORT;
            } else if (type.isAssignableFrom(Character.class) || type.isAssignableFrom(char.class)) {
                return CHAR;
            } else if (type.isAssignableFrom(Integer.class) || type.isAssignableFrom(int.class)) {
                return INT;
            } else if (type.isAssignableFrom(Long.class) || type.isAssignableFrom(long.class)) {
                return LONG;
            } else if (type.isAssignableFrom(Float.class) || type.isAssignableFrom(float.class)) {
                return FLOAT;
            } else if (type.isAssignableFrom(Double.class) || type.isAssignableFrom(double.class)) {
                return DOUBLE;
            } else if (type.isAssignableFrom(BigInteger.class)) {
                return BIG_INT;
            } else if (type.isAssignableFrom(BigDecimal.class)) {
                return BIG_DECIMAL;
            }

            return UNKNOWN;
        }
    }
}
