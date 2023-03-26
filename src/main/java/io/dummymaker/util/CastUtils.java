package io.dummymaker.util;

import io.dummymaker.generator.Generator;
import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import org.jetbrains.annotations.NotNull;

/**
 * Utils for object casting
 *
 * @author Anton Kurako (GoodforGod)
 * @since 06.03.2018
 */
public final class CastUtils {

    private CastUtils() {}

    public static Object castToNumber(Object value, Class<?> fieldType) {
        try {
            switch (CastType.of(fieldType)) {
                case BYTE:
                    return Byte.valueOf(String.valueOf(value));
                case SHORT:
                    final long shortTemp = Long.parseLong(String.valueOf(value));
                    if (shortTemp > Short.MAX_VALUE) {
                        return Short.MAX_VALUE;
                    } else if (shortTemp < Short.MIN_VALUE) {
                        return Short.MIN_VALUE;
                    } else {
                        return (short) shortTemp;
                    }
                case INT:
                    final long intTemp = Long.parseLong(String.valueOf(value));
                    if (intTemp > Integer.MAX_VALUE) {
                        return Integer.MAX_VALUE;
                    } else if (intTemp < Integer.MIN_VALUE) {
                        return Integer.MIN_VALUE;
                    } else {
                        return (int) intTemp;
                    }
                case LONG:
                    return Long.valueOf(String.valueOf(value));
                case FLOAT:
                    return Float.valueOf(String.valueOf(value));
                case DOUBLE:
                    return Double.valueOf(String.valueOf(value));
                case BIG_INT:
                    return new BigInteger(String.valueOf(value));
                case BIG_DECIMAL:
                    return new BigDecimal(String.valueOf(value));
                case CHAR:
                    try {
                        return Character.forDigit(Integer.parseInt(String.valueOf(value)), 10);
                    } catch (Exception e) {
                        return String.valueOf(value).charAt(0);
                    }
                case BOOLEAN:
                    final String b = String.valueOf(value);
                    if ("1".equals(b)) {
                        return true;
                    } else if ("0".equals(b)) {
                        return false;
                    } else {
                        return Boolean.valueOf(String.valueOf(value));
                    }
                default:
                    return value;
            }
        } catch (NumberFormatException e) {
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
    public static <T> T instantiate(Class<T> tClass, T defaultEntity) {
        final T t = instantiate(tClass);
        return (t == null)
                ? defaultEntity
                : t;
    }

    /**
     * Instantiate class if possible, or return default provided value
     *
     * @param target class to instantiate
     * @param <T>    class instance
     * @return class instance
     */
    @SuppressWarnings("unchecked")
    public static <T> T instantiate(Class<T> target) {
        if (target == null) {
            return null;
        }

        final Constructor<?> constructor = Arrays.stream(target.getDeclaredConstructors())
                .filter(c -> c.getParameterCount() == 0) // search for zero arg constructor
                .findFirst()
                .orElse(Arrays.stream(target.getDeclaredConstructors())
                        .filter(c -> c.getParameterCount() == 1) // search for potential inner class constructor
                        .findFirst().orElse(null));

        try {
            if (constructor == null) {
                throw new IllegalStateException(
                        "Can not instantiate '" + target.getCanonicalName() + "', zero argument constructor not found");
            }

            constructor.setAccessible(true);
            if (constructor.getParameterTypes().length > 0) {
                final Class<?> parentType = constructor.getParameterTypes()[0];
                if (!CastType.of(parentType).equals(CastType.UNKNOWN)) {
                    throw new IllegalStateException(
                            "Can not instantiate '" + target.getCanonicalName() + "', zero argument constructor not found");
                }

                final Object parent = instantiate(parentType);
                if (parent == null)
                    return null;

                return ((T) constructor.newInstance(parent));
            } else {
                return ((T) constructor.newInstance());
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
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
    public static <T> T generateObject(Generator generator, Class<T> fieldType) {
        if (generator == null)
            return null;

        final Object object = generator.get();
        return castObject(object, fieldType);
    }

    /**
     * Extracts generic type
     *
     * @param type to extract from
     * @return actual type or object if length is not presented
     */
    public static Type getGenericType(Type type) {
        return getGenericType(type, 0);
    }

    /**
     * Extracts generic type
     *
     * @param type        to extract from
     * @param paramNumber actual param number in parameterized type array
     * @return actual type or object if length is not presented
     */
    public static Type getGenericType(Type type, int paramNumber) {
        try {
            final ParameterizedType parameterizedType = ((ParameterizedType) type);
            return (parameterizedType.getActualTypeArguments().length < paramNumber + 1)
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
     * @return generated and cast object
     */
    @SuppressWarnings("unchecked")
    public static <T> T castObject(Object castObject, Class<T> fieldType) {
        if (fieldType == null)
            return null;

        final boolean isTypeString = String.class.equals(fieldType) || CharSequence.class.isAssignableFrom(fieldType);
        if (castObject == null)
            return isTypeString
                    ? ((T) "null")
                    : null;

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
     * Try to box downcast or box object if it is boxed primitive type And field is also boxed primitive
     * (can't cast one to another explicitly)
     */
    private static <T> Object boxObject(Object castObject, Class<T> fieldType) {
        final CastType firstType = CastType.of(castObject.getClass());
        final CastType secondType = CastType.of(fieldType);

        return (secondType.equals(CastType.UNKNOWN) || firstType.equals(CastType.UNKNOWN))
                ? castObject
                : castToNumber(castObject, fieldType);
    }

    /**
     * Check if objects have equals types, even if they are primitive
     */
    private static boolean areEquals(Class<?> firstClass, Class<?> secondClass) {
        final CastType firstType = CastType.of(firstClass);
        final CastType secondType = CastType.of(secondClass);

        return (firstType.equals(CastType.UNKNOWN) || secondType.equals(CastType.UNKNOWN))
                ? firstClass.equals(secondClass)
                : firstType.equals(secondType);
    }

    @SuppressWarnings("unchecked")
    private static <T> T castObject(Object castObject,
                                    Class<T> fieldType,
                                    boolean isTypeAssignable,
                                    boolean isTypeEquals,
                                    boolean isTypeObject,
                                    boolean isTypeString) {
        if (isTypeEquals || isTypeObject)
            return ((T) castObject);
        else if (isTypeString)
            return ((T) String.valueOf(castObject));
        else if (isTypeAssignable)
            return fieldType.cast(castObject);

        // Try to force cast anyway
        try {
            return fieldType.cast(castObject);
        } catch (Exception e) {
            return null;
        }
    }

    public enum CastType {

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

        public static CastType of(Class<?> type) {
            if (String.class.isAssignableFrom(type) || CharSequence.class.isAssignableFrom(type)) {
                return STRING;
            } else if (Boolean.class.isAssignableFrom(type) || boolean.class.isAssignableFrom(type)) {
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
            } else if (BigInteger.class.isAssignableFrom(type)) {
                return BIG_INT;
            } else if (BigDecimal.class.isAssignableFrom(type)) {
                return BIG_DECIMAL;
            }

            return UNKNOWN;
        }
    }

    public static boolean isPrimitive(@NotNull Class<?> type) {
        return type.isAssignableFrom(boolean.class)
                || type.isAssignableFrom(byte.class)
                || type.isAssignableFrom(short.class)
                || type.isAssignableFrom(char.class)
                || type.isAssignableFrom(int.class)
                || type.isAssignableFrom(long.class)
                || type.isAssignableFrom(float.class)
                || type.isAssignableFrom(double.class);
    }
}
