package io.dummymaker.generator.parameterized;

import io.dummymaker.factory.refactored.GenType;
import io.dummymaker.factory.refactored.ParameterizedGenerator;
import io.dummymaker.factory.refactored.TypeBuilder;
import io.dummymaker.generator.simple.ObjectGenerator;
import io.dummymaker.util.RandomUtils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 15.12.2022
 */
public final class ListParameterizedGenerator implements ParameterizedGenerator<Object> {

    private static final ObjectGenerator DEFAULT_GENERATOR = new ObjectGenerator();

    private final int min;
    private final int max;
    private final int fixed;

    public ListParameterizedGenerator(int min, int max) {
        this(min, max, -1);
    }

    public ListParameterizedGenerator(int min, int max, int fixed) {
        this.min = min;
        this.max = max;
        this.fixed = fixed;
    }

    @Override
    public Object get(@NotNull GenType fieldType, @NotNull TypeBuilder typeBuilder) {
        if(fieldType.generics().isEmpty()) {
            return get();
        }

        final int size = (fixed == -1)
                ? RandomUtils.random(min, max)
                : fixed;

        List<Object> collector = Collections.emptyList();
        for (int i = 0; i < size; i++) {
            final Object element = typeBuilder.build(fieldType.generics().get(0).value());
            if(element != null) {
                if(collector.isEmpty()) {
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

        final List<Object> collector = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            collector.add(DEFAULT_GENERATOR.get());
        }

        return collector;
    }

    private <T> List<T> buildCollector(@NotNull GenType fieldType, int size) {
        if (LinkedList.class.equals(fieldType.value())) {
            return new LinkedList<>();
        } else if (CopyOnWriteArrayList.class.equals(fieldType.value())) {
            return new CopyOnWriteArrayList<>();
        }

        return new ArrayList<>(size);
    }
}
