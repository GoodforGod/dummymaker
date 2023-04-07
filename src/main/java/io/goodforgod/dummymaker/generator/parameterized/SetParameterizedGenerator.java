package io.goodforgod.dummymaker.generator.parameterized;

import io.goodforgod.dummymaker.GenType;
import io.goodforgod.dummymaker.generator.GenParameters;
import io.goodforgod.dummymaker.generator.Generator;
import io.goodforgod.dummymaker.generator.ParameterizedGenerator;
import io.goodforgod.dummymaker.generator.simple.ObjectGenerator;
import io.goodforgod.dummymaker.util.RandomUtils;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArraySet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 15.12.2022
 */
public final class SetParameterizedGenerator implements ParameterizedGenerator<Object> {

    private static final ObjectGenerator DEFAULT_GENERATOR = new ObjectGenerator();

    private final int min;
    private final int max;
    private final int fixed;

    @Nullable
    private final Generator<?> generator;

    public SetParameterizedGenerator(int min, int max) {
        this(min, max, -1, null);
    }

    public SetParameterizedGenerator(int min, int max, @Nullable Generator<?> generator) {
        this(min, max, -1, generator);
    }

    public SetParameterizedGenerator(int fixed, @Nullable Generator<?> generator) {
        this(1, 3, fixed, generator);
    }

    public SetParameterizedGenerator(int min, int max, int fixed, @Nullable Generator<?> generator) {
        if (min < 1) {
            throw new IllegalArgumentException("Min can't be less than 1, but was: " + min);
        } else if (max < min) {
            throw new IllegalArgumentException("Max can't be less than Min, but was " + max + " when Min was " + min);
        }

        this.min = min;
        this.max = max;
        this.fixed = fixed;
        this.generator = generator;
    }

    @Override
    public Object get(@NotNull GenParameters parameters) {
        if (parameters.parameterType().generics().isEmpty()) {
            return get();
        }

        final int size = (fixed < 1)
                ? RandomUtils.random(min, max)
                : fixed;

        Set<Object> collector = Collections.emptySet();
        final Class<?> paramTypeRaw = parameters.parameterType().generics().get(0).raw();
        for (int i = 0; i < size; i++) {
            final Object element = (generator != null)
                    ? generator.get()
                    : parameters.genericBuilder().build(paramTypeRaw);

            if (element != null) {
                if (collector.isEmpty()) {
                    collector = buildCollector(parameters.parameterType(), size);
                }

                collector.add(element);
            }
        }

        return collector;
    }

    @Override
    public Object get() {
        final int size = (fixed < 1)
                ? RandomUtils.random(min, max)
                : fixed;

        final Set<Object> collector = new HashSet<>(size);
        for (int i = 0; i < size; i++) {
            final Object value = (generator != null)
                    ? generator.get()
                    : DEFAULT_GENERATOR.get();

            collector.add(value);
        }

        return collector;
    }

    private <T> Set<T> buildCollector(@NotNull GenType fieldType, int size) {
        if (TreeSet.class.equals(fieldType.raw())) {
            return new TreeSet<>();
        } else if (ConcurrentSkipListSet.class.equals(fieldType.raw())) {
            return new ConcurrentSkipListSet<>();
        } else if (LinkedHashSet.class.equals(fieldType.raw())) {
            return new LinkedHashSet<>(size);
        } else if (CopyOnWriteArraySet.class.equals(fieldType.raw())) {
            return new CopyOnWriteArraySet<>();
        }

        return new HashSet<>(size);
    }
}
