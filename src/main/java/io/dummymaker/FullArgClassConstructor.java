package io.dummymaker;

import io.dummymaker.error.GenConstructionException;
import io.dummymaker.generator.Generator;
import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Comparator;
import org.jetbrains.annotations.NotNull;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 13.11.2022
 */
final class FullArgClassConstructor implements ClassConstructor {

    private final GeneratorSupplier supplier;

    FullArgClassConstructor(GeneratorSupplier supplier) {
        this.supplier = supplier;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T instantiate(@NotNull Class<T> target) {
        final Constructor<?> constructor = Arrays.stream(target.getDeclaredConstructors())
                .max(Comparator.comparing(Constructor::getParameterCount))
                .orElseThrow(() -> new GenConstructionException("Suitable constructor not found for: " + target));

        try {
            constructor.setAccessible(true);
            if (constructor.getParameterTypes().length == 0) {
                return ((T) constructor.newInstance());
            }

            final Object[] typeParameters = Arrays.stream(constructor.getParameters())
                    .map(parameter -> supplier.get(parameter.getType(), parameter.getName()))
                    .map(Generator::get)
                    .toArray(Object[]::new);

            return ((T) constructor.newInstance(typeParameters));
        } catch (GenConstructionException e) {
            throw e;
        } catch (Exception e) {
            throw new GenConstructionException(
                    "Exception occurred during '" + target + "' class instantiation with full argument constructor due to: ", e);
        }
    }
}
