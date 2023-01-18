package io.dummymaker.factory;

import io.dummymaker.error.ClassConstructorException;
import io.dummymaker.util.CastUtils;
import java.lang.reflect.Constructor;
import java.util.Arrays;
import org.jetbrains.annotations.NotNull;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 13.11.2022
 */
final class ZeroArgClassConstructor implements ClassConstructor {

    @Override
    public <T> T instantiate(@NotNull Class<T> target) {
        final Constructor<?> constructor = Arrays.stream(target.getDeclaredConstructors())
                .filter(c -> c.getParameterCount() == 0) // search for zero arg constructor
                .findFirst()
                .orElse(Arrays.stream(target.getDeclaredConstructors())
                        .filter(c -> c.getParameterCount() == 1) // search for potential inner class constructor
                        .findFirst()
                        .orElseThrow(() -> new ClassConstructorException(
                                "Can't instantiate, zero argument constructor not found for: " + target)));

        try {
            constructor.setAccessible(true);
            if (constructor.getParameterTypes().length == 0) {
                return ((T) constructor.newInstance());
            }

            final Class<?> parentType = constructor.getParameterTypes()[0];
            if (!CastUtils.CastType.of(parentType).equals(CastUtils.CastType.UNKNOWN)) {
                throw new ClassConstructorException("Can't instantiate '" + target + "', zero argument constructor not found");
            }

            final Object parent = instantiate(parentType);
            return ((T) constructor.newInstance(parent));
        } catch (ClassConstructorException e) {
            throw e;
        } catch (Exception e) {
            throw new ClassConstructorException(e);
        }
    }
}
