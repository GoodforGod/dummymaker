package io.dummymaker.generator.parameterized;

import io.dummymaker.factory.refactored.GenType;
import io.dummymaker.factory.refactored.ParameterizedGenerator;
import io.dummymaker.factory.refactored.TypeBuilder;
import io.dummymaker.generator.Generator;
import io.dummymaker.generator.simple.ObjectGenerator;
import io.dummymaker.util.RandomUtils;
import java.lang.reflect.Array;
import java.util.function.Supplier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 15.12.2022
 */
public final class ArrayParameterizedGenerator implements ParameterizedGenerator<Object> {

    private static final ObjectGenerator DEFAULT_GENERATOR = new ObjectGenerator();

    private final int min;
    private final int max;
    private final int fixed;

    @Nullable
    private final Generator<?> generator;

    public ArrayParameterizedGenerator(int min, int max) {
        this(min, max, -1, null);
    }

    public ArrayParameterizedGenerator(int min, int max, int fixed, @Nullable Generator<?> generator) {
        this.min = min;
        this.max = max;
        this.fixed = fixed;
        this.generator = generator;
    }

    @Override
    public Object get(@NotNull GenType fieldType, @NotNull TypeBuilder typeBuilder) {
        if (!fieldType.raw().getTypeName().endsWith("[]")) {
            return null;
        }

        final Class<?> componentType = fieldType.raw().getComponentType();
        return getCollector(componentType, () -> (generator != null)
                ? generator.get()
                : typeBuilder.build(componentType));
    }

    @Override
    public Object get() {
        return getCollector(Object.class, DEFAULT_GENERATOR);
    }

    private Object getCollector(Class<?> arrayType, Supplier<Object> supplier) {
        final int size = (fixed == -1)
                ? RandomUtils.random(min, max)
                : fixed;

        final Object firstElement = supplier.get();
        if (firstElement == null) {
            return Array.newInstance(arrayType, 0);
        }

        Object collector = Array.newInstance(arrayType, size);
        Array.set(collector, 0, firstElement);
        for (int i = 1; i < size; i++) {
            final Object element = supplier.get();
            Array.set(collector, i, element);
        }

        return collector;
    }
}
