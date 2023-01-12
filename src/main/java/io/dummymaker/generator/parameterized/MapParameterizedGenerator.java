package io.dummymaker.generator.parameterized;

import io.dummymaker.factory.refactored.GenType;
import io.dummymaker.factory.refactored.ParameterizedGenerator;
import io.dummymaker.factory.refactored.TypeBuilder;
import io.dummymaker.generator.Generator;
import io.dummymaker.generator.simple.ObjectGenerator;
import io.dummymaker.util.RandomUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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

    @Nullable
    private final Generator<?> keyGenerator;
    @Nullable
    private final Generator<?> valueGenerator;

    public MapParameterizedGenerator(int min, int max) {
        this(min, max, -1, null, null);
    }

    public MapParameterizedGenerator(int min, int max, int fixed, @Nullable Generator<?> keyGenerator, @Nullable Generator<?> valueGenerator) {
        this.min = min;
        this.max = max;
        this.fixed = fixed;
        this.keyGenerator = keyGenerator;
        this.valueGenerator = valueGenerator;
    }

    @Override
    public Object get(@NotNull GenType fieldType, @NotNull TypeBuilder typeBuilder) {
        if (fieldType.generics().size() != 2) {
            return get();
        }

        final int size = (fixed == -1)
                ? RandomUtils.random(min, max)
                : fixed;

        final GenType keyType = fieldType.generics().get(0);
        final GenType valueType = fieldType.generics().get(1);

        Map<Object, Object> collector = Collections.emptyMap();
        for (int i = 0; i < size; i++) {
            final Object key = (keyGenerator != null)
                    ? keyGenerator.get()
                    : typeBuilder.build(keyType.raw());

            final Object value = (valueGenerator != null)
                    ? valueGenerator.get()
                    : typeBuilder.build(valueType.raw());

            if (key != null) {
                if (collector.isEmpty()) {
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
            final Object key = (keyGenerator != null)
                    ? keyGenerator.get()
                    : DEFAULT_GENERATOR.get();

            final Object value = (valueGenerator != null)
                    ? valueGenerator.get()
                    : DEFAULT_GENERATOR.get();

            collector.put(key, value);
        }

        return collector;
    }

    private <K, V> Map<K, V> buildCollector(@NotNull GenType fieldType, int size) {
        if (IdentityHashMap.class.equals(fieldType.raw())) {
            return new IdentityHashMap<>(size);
        } else if (LinkedHashMap.class.equals(fieldType.raw())) {
            return new LinkedHashMap<>(size);
        } else if (WeakHashMap.class.equals(fieldType.raw())) {
            return new WeakHashMap<>(size);
        } else if (ConcurrentHashMap.class.equals(fieldType.raw())) {
            return new ConcurrentHashMap<>(size);
        } else if (TreeMap.class.equals(fieldType.raw())) {
            return new TreeMap<>();
        } else if (ConcurrentSkipListMap.class.equals(fieldType.raw())) {
            return new ConcurrentSkipListMap<>();
        }

        return new HashMap<>(size);
    }
}
