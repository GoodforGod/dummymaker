package io.dummymaker.generator.complex;

import io.dummymaker.annotation.complex.GenMap;
import io.dummymaker.annotation.special.GenEmbedded;
import io.dummymaker.factory.IGenStorage;
import io.dummymaker.generator.IComplexGenerator;
import io.dummymaker.generator.IGenerator;
import io.dummymaker.generator.simple.string.IdGenerator;
import io.dummymaker.util.CollectionUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;

import static io.dummymaker.util.CastUtils.getGenericType;

/**
 * "default comment"
 *
 * @author GoodforGod
 * @see GenMap
 * @see IComplexGenerator
 * @see CollectionComplexGenerator
 * @since 22.04.2018
 */
public class MapComplexGenerator extends BasicComplexGenerator {

    @SuppressWarnings("Duplicates")
    @Override
    public @Nullable Object generate(final @NotNull Class<?> parent,
                                     final @NotNull Field field,
                                     final @NotNull IGenStorage storage,
                                     final Annotation annotation,
                                     final int depth) {
        if (!field.getType().isAssignableFrom(Map.class))
            return null;

        final Class<?> keyType = (Class<?>) getGenericType(field.getGenericType(), 0);
        final Class<?> valueType = (Class<?>) getGenericType(field.getGenericType(), 1);
        if (annotation == null) {
            final int size = CollectionUtils.random(MIN_DEFAULT, MAX_DEFAULT);
            final Class<? extends IGenerator> keySuitable = suitable(storage, field, keyType);
            final Class<? extends IGenerator> valueSuitable = suitable(storage, field, valueType);
            final int maxDepth = storage.getDepth(parent, field.getType());
            return generateMap(size, field, keySuitable, valueSuitable, keyType, valueType, storage, depth, maxDepth);
        }

        final GenMap a = ((GenMap) annotation);
        final Class<? extends IGenerator> keyGenerator = isGenDefault(a.key())
                ? suitable(storage, field, keyType)
                : a.key();

        final Class<? extends IGenerator> valueGenerator = isGenDefault(a.value())
                ? suitable(storage, field, keyType)
                : a.value();

        final int size = getDesiredSize(a.min(), a.max(), a.fixed());
        return generateMap(size, field, keyGenerator, valueGenerator, keyType, valueType, storage, depth, a.depth());
    }

    @Override
    public @NotNull Object generate() {
        final int size = CollectionUtils.random(MIN_DEFAULT, MAX_DEFAULT);
        return generateMap(size, null, IdGenerator.class, IdGenerator.class, Object.class, Object.class, null, GenEmbedded.MAX,
                1);
    }

    @SuppressWarnings("unchecked")
    private @NotNull Map generateMap(final int size,
                                     final Field field,
                                     final Class<? extends IGenerator> keyGenerator,
                                     final Class<? extends IGenerator> valueGenerator,
                                     final Class<?> keyFieldType,
                                     final Class<?> valueFieldType,
                                     final IGenStorage storage,
                                     final int depth,
                                     final int maxDepth) {

        // Firstly try to generate initial object, so we won't allocate map if not
        // necessary
        final Object initialKey = generateValue(keyGenerator, keyFieldType, storage, depth, maxDepth);
        final Object initialValue = generateValue(valueGenerator, valueFieldType, storage, depth, maxDepth);
        if (initialKey == null && initialValue == null)
            return Collections.emptyMap();

        final Map map = buildMap(field, size);
        map.put(initialKey, initialValue);
        for (int i = 0; i < size - 1; i++) {
            final Object key = generateValue(keyGenerator, keyFieldType, storage, depth, maxDepth);
            final Object value = generateValue(valueGenerator, valueFieldType, storage, depth, maxDepth);

            if (key != null && value != null) {
                map.put(key, value);
            }
        }

        return map;
    }

    private Map buildMap(Field field, int size) {
        if (field == null)
            return new HashMap<>(size);

        if (IdentityHashMap.class.equals(field.getType())) {
            return new IdentityHashMap<>(size);
        } else if (LinkedHashMap.class.equals(field.getType())) {
            return new LinkedHashMap<>(size);
        } else if (WeakHashMap.class.equals(field.getType())) {
            return new WeakHashMap<>(size);
        } else if (ConcurrentHashMap.class.equals(field.getType())) {
            return new ConcurrentHashMap<>(size);
        } else if (TreeMap.class.equals(field.getType())) {
            return new TreeMap<>();
        } else if (ConcurrentSkipListMap.class.equals(field.getType())) {
            return new ConcurrentSkipListMap<>();
        }

        return new HashMap<>(size);
    }
}
