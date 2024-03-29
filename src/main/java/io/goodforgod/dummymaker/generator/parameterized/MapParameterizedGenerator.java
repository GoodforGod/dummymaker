package io.goodforgod.dummymaker.generator.parameterized;

import io.goodforgod.dummymaker.GenType;
import io.goodforgod.dummymaker.generator.GenParameters;
import io.goodforgod.dummymaker.generator.Generator;
import io.goodforgod.dummymaker.generator.ParameterizedGenerator;
import io.goodforgod.dummymaker.generator.simple.ObjectGenerator;
import io.goodforgod.dummymaker.util.RandomUtils;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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

    public MapParameterizedGenerator(int min,
                                     int max,
                                     @Nullable Generator<?> keyGenerator,
                                     @Nullable Generator<?> valueGenerator) {
        this(min, max, -1, keyGenerator, valueGenerator);
    }

    public MapParameterizedGenerator(int fixed,
                                     @Nullable Generator<?> keyGenerator,
                                     @Nullable Generator<?> valueGenerator) {
        this(1, 3, fixed, keyGenerator, valueGenerator);
    }

    public MapParameterizedGenerator(int min,
                                     int max,
                                     int fixed,
                                     @Nullable Generator<?> keyGenerator,
                                     @Nullable Generator<?> valueGenerator) {
        if (min < 1) {
            throw new IllegalArgumentException("Min can't be less than 1, but was: " + min);
        } else if (max < min) {
            throw new IllegalArgumentException("Max can't be less than Min, but was " + max + " when Min was " + min);
        }

        this.min = min;
        this.max = max;
        this.fixed = fixed;
        this.keyGenerator = keyGenerator;
        this.valueGenerator = valueGenerator;
    }

    @Override
    public Object get(@NotNull GenParameters parameters) {
        if (parameters.parameterType().generics().size() != 2) {
            return get();
        }

        final int size = (fixed < 1)
                ? RandomUtils.random(min, max)
                : fixed;

        final GenType keyType = parameters.parameterType().generics().get(0);
        final GenType valueType = parameters.parameterType().generics().get(1);

        Map<Object, Object> collector = Collections.emptyMap();
        for (int i = 0; i < size; i++) {
            final Object key = (keyGenerator != null)
                    ? keyGenerator.get()
                    : parameters.genericBuilder().build(keyType.raw());

            final Object value = (valueGenerator != null)
                    ? valueGenerator.get()
                    : parameters.genericBuilder().build(valueType.raw());

            if (key != null) {
                if (collector.isEmpty()) {
                    collector = buildCollector(parameters.parameterType(), size);
                }

                collector.put(key, value);
            }
        }

        return collector;
    }

    @Override
    public Object get() {
        final int size = (fixed < 1)
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
