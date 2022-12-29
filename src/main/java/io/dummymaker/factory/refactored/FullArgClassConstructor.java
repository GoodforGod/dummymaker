package io.dummymaker.factory.refactored;

import io.dummymaker.error.ClassConstructorException;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Supplier;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 13.11.2022
 */
final class FullArgClassConstructor implements ClassConstructor {

    private final GenGeneratorSupplier supplier;

    FullArgClassConstructor(GenGeneratorSupplier supplier) {
        this.supplier = supplier;
    }

    @Override
    public <T> T instantiate(@NotNull Class<T> target) {
        final Constructor<?> constructor = Arrays.stream(target.getDeclaredConstructors())
                .max(Comparator.comparing(Constructor::getParameterCount))
                .orElseThrow(() -> new ClassConstructorException("Suitable constructor not found for: " + target));

        try {
            constructor.setAccessible(true);
            if (constructor.getParameterTypes().length == 0) {
                return ((T) constructor.newInstance());
            }

            final Object[] typeParameters = Arrays.stream(constructor.getParameterTypes())
                    .map(supplier::get)
                    .map(Supplier::get)
                    .toArray(Object[]::new);

            return ((T) constructor.newInstance(typeParameters));
        } catch (ClassConstructorException e) {
            throw e;
        } catch (Exception e) {
            throw new ClassConstructorException(e);
        }
    }
}
