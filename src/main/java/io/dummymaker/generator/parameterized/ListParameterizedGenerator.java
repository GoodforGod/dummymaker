package io.dummymaker.generator.parameterized;

import io.dummymaker.factory.GenType;
import io.dummymaker.factory.GenTypeBuilder;
import io.dummymaker.generator.Generator;
import io.dummymaker.generator.Localisation;
import io.dummymaker.generator.ParameterizedGenerator;
import io.dummymaker.generator.simple.ObjectGenerator;
import io.dummymaker.util.RandomUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 15.12.2022
 */
public final class ListParameterizedGenerator implements ParameterizedGenerator<Object> {

    private static final ObjectGenerator DEFAULT_GENERATOR = new ObjectGenerator();

    private final int min;
    private final int max;
    private final int fixed;

    @Nullable
    private final Generator<?> generator;

    public ListParameterizedGenerator(int min, int max) {
        this(min, max, -1, null);
    }

    public ListParameterizedGenerator(int min, int max, int fixed, @Nullable Generator<?> generator) {
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
    public Object get(@NotNull Localisation localisation, @NotNull GenType fieldType, @NotNull GenTypeBuilder typeBuilder) {
        if (fieldType.generics().isEmpty()) {
            return get();
        }

        final int size = (fixed < 1)
                ? RandomUtils.random(min, max)
                : fixed;

        List<Object> collector = Collections.emptyList();
        for (int i = 0; i < size; i++) {
            final Object element = (generator != null)
                    ? generator.get()
                    : typeBuilder.build(fieldType.generics().get(0).raw());

            if (element != null) {
                if (collector.isEmpty()) {
                    collector = buildCollector(fieldType, size);
                }

                collector.add(element);
            }
        }

        return collector;
    }

    @Override
    public Object get(@NotNull Localisation localisation) {
        final int size = (fixed < 1)
                ? RandomUtils.random(min, max)
                : fixed;

        final List<Object> collector = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            final Object value = (generator != null)
                    ? generator.get()
                    : DEFAULT_GENERATOR.get();

            collector.add(value);
        }

        return collector;
    }

    private <T> List<T> buildCollector(@NotNull GenType fieldType, int size) {
        if (LinkedList.class.equals(fieldType.raw())) {
            return new LinkedList<>();
        } else if (CopyOnWriteArrayList.class.equals(fieldType.raw())) {
            return new CopyOnWriteArrayList<>();
        }

        return new ArrayList<>(size);
    }
}
