package io.dummymaker.util;

import io.dummymaker.generator.Generator;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Utils for auto gen mapping
 *
 * @author Anton Kurako (GoodforGod)
 * @since 26.04.2018
 */
public final class GenUtils {

    private GenUtils() {}

    public static List<Class> getInterfaceType(Type type) {
        try {
            return Arrays.asList((Class<?>) ((ParameterizedType) type).getActualTypeArguments()[0]);
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public static boolean isGenerator(Type type) {
        try {
            return ((ParameterizedType) type).getRawType().equals(Generator.class);
        } catch (Exception e) {
            return false;
        }
    }

    public static List<Type> getTypes(Class<?> target) {
        if (Object.class.equals(target)) {
            return Collections.emptyList();
        }

        try {
            final List<Type> types = getTypesFromClass(target);
            if (target.getSuperclass() != null)
                types.addAll(getTypes(target.getSuperclass()));

            return types;
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    private static List<Type> getTypesFromInterfaces(Type targetType) {
        if (isGenerator(targetType) || ParameterizedType.class.equals(targetType)) {
            return Collections.emptyList();
        }

        final Class targetClass = (Class) targetType;
        return getTypesFromClass(targetClass);
    }

    private static List<Type> getTypesFromClass(Class targetClass) {
        final List<Type> types = Arrays.stream((targetClass).getGenericInterfaces()).collect(Collectors.toList());
        final List<Type> collect = types.stream()
                .filter(i -> !ParameterizedType.class.equals(i))
                .map(GenUtils::getTypesFromInterfaces)
                .flatMap(List::stream)
                .collect(Collectors.toList());

        types.addAll(collect);
        return types;
    }
}
