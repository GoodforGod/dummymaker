package io.dummymaker.generator.parameterized;

import io.dummymaker.factory.refactored.GenType;
import io.dummymaker.factory.refactored.ParameterizedGenerator;
import io.dummymaker.factory.refactored.TypeBuilder;
import io.dummymaker.generator.Generator;
import io.dummymaker.generator.simple.ObjectGenerator;
import io.dummymaker.util.RandomUtils;
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

    public SetParameterizedGenerator(int min, int max, int fixed, @Nullable Generator<?> generator) {
        this.min = min;
        this.max = max;
        this.fixed = fixed;
        this.generator = generator;
    }

    @Override
    public Object get(@NotNull GenType fieldType, @NotNull TypeBuilder typeBuilder) {
        if (fieldType.generics().isEmpty()) {
            return get();
        }

        final int size = (fixed == -1)
                ? RandomUtils.random(min, max)
                : fixed;

        Set<Object> collector = Collections.emptySet();
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
    public Object get() {
        final int size = (fixed == -1)
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