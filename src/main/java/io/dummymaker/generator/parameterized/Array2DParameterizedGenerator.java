package io.dummymaker.generator.parameterized;

import io.dummymaker.factory.GenType;
import io.dummymaker.factory.GenTypeBuilder;
import io.dummymaker.generator.Generator;
import io.dummymaker.generator.ParameterizedGenerator;
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
public final class Array2DParameterizedGenerator implements ParameterizedGenerator<Object> {

    private static final ObjectGenerator DEFAULT_GENERATOR = new ObjectGenerator();

    private final int minFirst;
    private final int maxFirst;
    private final int fixedFirst;

    private final int minSecond;
    private final int maxSecond;
    private final int fixedSecond;

    @Nullable
    private final Generator<?> generator;

    public Array2DParameterizedGenerator(int minFirst, int maxFirst, int minSecond, int maxSecond) {
        this(minFirst, maxFirst, -1, minSecond, maxSecond, -1, null);
    }

    public Array2DParameterizedGenerator(int minFirst,
                                         int maxFirst,
                                         int fixedFirst,
                                         int minSecond,
                                         int maxSecond,
                                         int fixedSecond,
                                         @Nullable Generator<?> generator) {
        if (minFirst < 1) {
            throw new IllegalArgumentException("Min can't be less than 1, but was: " + minFirst);
        } else if (maxFirst < minFirst) {
            throw new IllegalArgumentException("Max can't be less than Min, but was " + maxFirst + " when Min was " + minFirst);
        } else if (minSecond < 1) {
            throw new IllegalArgumentException("Min can't be less than 1, but was: " + minSecond);
        } else if (maxSecond < minSecond) {
            throw new IllegalArgumentException("Max can't be less than Min, but was " + maxSecond + " when Min was " + minSecond);
        }

        this.minFirst = minFirst;
        this.maxFirst = maxFirst;
        this.fixedFirst = fixedFirst;
        this.minSecond = minSecond;
        this.maxSecond = maxSecond;
        this.fixedSecond = fixedSecond;
        this.generator = generator;
    }

    @Override
    public Object get(@NotNull GenType fieldType, @NotNull GenTypeBuilder typeBuilder) {
        if (!fieldType.raw().getTypeName().endsWith("[][]")) {
            return null;
        }

        final Class<?> componentType = fieldType.raw().getComponentType().getComponentType();
        return getCollector(componentType, () -> (generator != null)
                ? generator.get()
                : typeBuilder.build(componentType));
    }

    @Override
    public Object get() {
        return getCollector(Object.class, DEFAULT_GENERATOR);
    }

    private Object getCollector(Class<?> arrayType, Supplier<Object> supplier) {
        final int sizeFirst = (fixedFirst < 1)
                ? RandomUtils.random(minFirst, maxFirst)
                : fixedFirst;

        final int sizeSecond = (fixedSecond < 1)
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
