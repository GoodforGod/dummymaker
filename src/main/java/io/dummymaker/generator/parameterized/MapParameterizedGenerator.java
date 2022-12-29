package io.dummymaker.generator.parameterized;

import io.dummymaker.factory.refactored.GenType;
import io.dummymaker.factory.refactored.ParameterizedGenerator;
import io.dummymaker.factory.refactored.TypeBuilder;
import io.dummymaker.generator.simple.ObjectGenerator;
import io.dummymaker.util.RandomUtils;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 15.12.2022
 */
public final class MapParameterizedGenerator implements ParameterizedGenerator<Object> {

    private static final ObjectGenerator DEFAULT_GENERATOR = new ObjectGenerator();

    private final int min;
    private final int max;
    private final int fixed;

    public MapParameterizedGenerator(int min, int max) {
        this(min, max, -1);
    }

    public MapParameterizedGenerator(int min, int max, int fixed) {
        this.min = min;
        this.max = max;
        this.fixed = fixed;
    }

    @Override
    public Object get(@NotNull GenType fieldType, @NotNull TypeBuilder typeBuilder) {
        if(fieldType.generics().size() != 2) {
            return get();
        }

        final int size = (fixed == -1)
                ? RandomUtils.random(min, max)
                : fixed;

        final GenType keyType = fieldType.generics().get(0);
        final GenType valueType = fieldType.generics().get(1);

        Map<Object, Object> collector = Collections.emptyMap();
        for (int i = 0; i < size; i++) {
            final Object key = typeBuilder.build(keyType.value());
            final Object value = typeBuilder.build(valueType.value());
            if(key != null) {
                if(collector.isEmpty()) {
                    collector = buildCollector(fieldType, size);
                }

                collector.put(key, value);
            }
        }

        return collector;
    }

    @Override
    public Object get() {
        final int size = (fixed == -1)
                ? RandomUtils.random(min, max)
                : fixed;

        final Map<Object, Object> collector = new HashMap<>(size);
        for (int i = 0; i < size; i++) {
            collector.put(DEFAULT_GENERATOR.get(), DEFAULT_GENERATOR.get());
        }

        return collector;
    }

    private <K, V> Map<K, V> buildCollector(@NotNull GenType fieldType, int size) {
        if (IdentityHashMap.class.equals(fieldType.value())) {
            return new IdentityHashMap<>(size);
        } else if (LinkedHashMap.class.equals(fieldType.value())) {
            return new LinkedHashMap<>(size);
        } else if (WeakHashMap.class.equals(fieldType.value())) {
            return new WeakHashMap<>(size);
        } else if (ConcurrentHashMap.class.equals(fieldType.value())) {
            return new ConcurrentHashMap<>(size);
        } else if (TreeMap.class.equals(fieldType.value())) {
            return new TreeMap<>();
        } else if (ConcurrentSkipListMap.class.equals(fieldType.value())) {
            return new ConcurrentSkipListMap<>();
        }

        return new HashMap<>(size);
    }
}
