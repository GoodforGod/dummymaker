package io.dummymaker.generator.complex.impl;

import io.dummymaker.annotation.complex.GenMap;
import io.dummymaker.annotation.special.GenEmbedded;
import io.dummymaker.container.impl.GeneratorsStorage;
import io.dummymaker.generator.simple.IGenerator;
import io.dummymaker.generator.simple.impl.string.IdGenerator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import static io.dummymaker.util.BasicCastUtils.getGenericType;
import static io.dummymaker.util.BasicGenUtils.getAutoGenerator;

/**
 * "default comment"
 *
 * @see GenMap
 * @see io.dummymaker.generator.complex.IComplexGenerator
 * @see CollectionComplexGenerator
 *
 * @author GoodforGod
 * @since 22.04.2018
 */
public class MapComplexGenerator extends BasicComplexGenerator {

    private Map generateMap(final int amount,
                            final Class<? extends IGenerator> keyGenerator,
                            final Class<? extends IGenerator> valueGenerator,
                            final Class<?> keyFieldType,
                            final Class<?> valueFieldType,
                            final GeneratorsStorage storage,
                            final int depth,
                            final int maxDepth) {

        // Firstly try to generate initial object, so we won't allocate list if not necessary
        final Object initialKey = generateValue(keyGenerator, keyFieldType, storage, depth, maxDepth);
        final Object initialValue = generateValue(valueGenerator, valueFieldType, storage, depth, maxDepth);
        if (initialKey == null && initialValue == null)
            return Collections.emptyMap();

        final Map map = new HashMap<>(amount);
        map.put(initialKey, initialValue);
        for (int i = 0; i < amount - 1; i++) {
            final Object key = generateValue(keyGenerator, keyFieldType, storage, depth, maxDepth);
            final Object value = generateValue(valueGenerator, valueFieldType, storage, depth, maxDepth);

            if (key != null && value != null) {
                map.put(key, value);
            }
        }

        return map;
    }

    @Override
    public Object generate(final Annotation annotation,
                           final Field field,
                           final GeneratorsStorage storage,
                           final int depth) {
        if (field == null || !field.getType().isAssignableFrom(Map.class))
            return null;

        final Class<?> keyType = (Class<?>) getGenericType(field.getGenericType(), 0);
        final Class<?> valueType = (Class<?>) getGenericType(field.getGenericType(), 1);
        if (annotation == null) {
            return generateMap(ThreadLocalRandom.current().nextInt(MIN_DEFAULT, MAX_DEFAULT),
                    getAutoGenerator(keyType),
                    getAutoGenerator(valueType),
                    keyType,
                    valueType,
                    storage,
                    depth,
                    1);
        }

        final GenMap a = ((GenMap) annotation);
        final Class<? extends IGenerator> keyGenerator = isGenDefault(a.key())
                ? getAutoGenerator(keyType)
                : a.key();

        final Class<? extends IGenerator> valueGenerator = isGenDefault(a.value())
                ? getAutoGenerator(valueType)
                : a.value();

        final int size = genRandomSize(a.min(), a.max(), a.fixed());
        return generateMap(size, keyGenerator, valueGenerator, keyType, valueType, storage, depth, a.depth());
    }

    @Override
    public Object generate() {
        return generateMap(ThreadLocalRandom.current().nextInt(MIN_DEFAULT, MAX_DEFAULT),
                IdGenerator.class,
                IdGenerator.class,
                Object.class,
                Object.class,
                null,
                GenEmbedded.MAX,
                1);
    }
}
