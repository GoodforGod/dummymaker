package io.dummymaker.generator.parameterized;

import io.dummymaker.factory.refactored.GenType;
import io.dummymaker.factory.refactored.ParameterizedGenerator;
import io.dummymaker.factory.refactored.TypeBuilder;
import io.dummymaker.generator.simple.ObjectGenerator;
import io.dummymaker.util.RandomUtils;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.util.function.Supplier;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 15.12.2022
 */
public final class Array2DParameterizedGenerator implements ParameterizedGenerator<Object> {

    private static final ObjectGenerator DEFAULT_GENERATOR = new ObjectGenerator();

    private final int minFirst;
    private final int maxFirst;
    private final int fixedFirst;

    private final int minSecond;
    private final int maxSecond;
    private final int fixedSecond;

    public Array2DParameterizedGenerator(int minFirst, int maxFirst, int minSecond, int maxSecond) {
        this(minFirst, maxFirst, -1, minSecond, maxSecond, -1);
    }

    public Array2DParameterizedGenerator(int minFirst, int maxFirst, int fixedFirst,
                                         int minSecond, int maxSecond, int fixedSecond) {
        this.minFirst = minFirst;
        this.maxFirst = maxFirst;
        this.fixedFirst = fixedFirst;
        this.minSecond = minSecond;
        this.maxSecond = maxSecond;
        this.fixedSecond = fixedSecond;
    }

    @Override
    public Object get(@NotNull GenType fieldType, @NotNull TypeBuilder typeBuilder) {
        if (fieldType.generics().isEmpty()) {
            return get();
        }

        return getCollector(fieldType.value(), () -> typeBuilder.build(fieldType.generics().get(0).value()));
    }

    @Override
    public Object get() {
        return getCollector(Object.class, DEFAULT_GENERATOR);
    }

    private Object getCollector(Class<?> arrayType, Supplier<Object> supplier) {
        final int sizeFirst = (fixedFirst == -1)
                ? RandomUtils.random(minFirst, maxFirst)
                : fixedFirst;

        final int sizeSecond = (fixedSecond == -1)
                ? RandomUtils.random(minSecond, maxSecond)
                : fixedSecond;

        final Object elementFirst = supplier.get();
        if (elementFirst == null) {
            return Array.newInstance(arrayType, 0, 0);
        }

        final Object collectorFirst = Array.newInstance(arrayType, sizeFirst, sizeSecond);
        for (int i = 0; i < sizeFirst; i++) {
            final Object collectorSecond = Array.get(collectorFirst, i);
            for (int j = 0; j < sizeSecond; j++) {
                final Object element = supplier.get();
                Array.set(collectorSecond, j, element);
            }
        }

        return collectorFirst;
    }
}
