package io.dummymaker;

import io.dummymaker.error.GenConstructionException;
import io.dummymaker.error.GenException;
import io.dummymaker.util.CastUtils;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 23.03.2023
 */
final class GenConstructor {

    private final GenType type;
    private final Constructor<?> constructor;

    GenConstructor(GenType type) {
        this.type = type;
        this.constructor = getConstructor(type.raw());
    }

    List<GenParameter> parameters() {
        return Arrays.stream(constructor.getParameters())
                .map(parameter -> new GenParameter(GenType.ofType(parameter.getParameterizedType()), parameter.getName()))
                .collect(Collectors.toList());
    }

    <T> T instantiate(Object... parameters) {
        try {
            constructor.setAccessible(true);
            return (T) constructor.newInstance(parameters);
        } catch (Exception e) {
            throw new GenConstructionException("Exception occurred during '" + type + "' class instantiation due to: ", e);
        }
    }

    private static Constructor<?> getConstructor(Class<?> target) {
        try {
            if (isRecord(target)) {
                final Class<?>[] constructorTypes = Arrays.stream(target.getDeclaredFields())
                        .map(Field::getType)
                        .toArray(Class<?>[]::new);

                return target.getDeclaredConstructor(constructorTypes);
            }

            // search for zero arg constructor
            final Optional<Constructor<?>> zeroArgConstructor = Arrays.stream(target.getDeclaredConstructors())
                    .filter(c -> c.getParameterCount() == 0)
                    .findFirst();
            if (zeroArgConstructor.isPresent()) {
                return zeroArgConstructor.get();
            }

            // search for potential inner class constructor
            final Optional<Constructor<?>> innerClassArgConstructor = Arrays.stream(target.getDeclaredConstructors())
                    .filter(c -> c.getParameterCount() == 1)
                    .filter(c -> CastUtils.CastType.of(c.getParameterTypes()[0]).equals(CastUtils.CastType.UNKNOWN))
                    .findFirst();
            if (innerClassArgConstructor.isPresent()) {
                return innerClassArgConstructor.get();
            }

            // search for full arg constructor
            final Optional<Constructor<?>> fullArgConstructor = Arrays.stream(target.getDeclaredConstructors())
                    .max(Comparator.comparingInt(Constructor::getParameterCount));
            if (fullArgConstructor.isPresent()) {
                return fullArgConstructor.get();
            }

            throw new GenConstructionException("Can't instantiate '" + target + "', suitable constructor not found");
        } catch (GenException e) {
            throw e;
        } catch (Exception e) {
            throw new GenConstructionException("Exception occurred during '" + target + "' class constructor search due to: ", e);
        }
    }

    private static boolean isRecord(Class<?> target) {
        return target.getSuperclass() != null && target.getSuperclass().getCanonicalName().equals("java.lang.Record");
    }
}
