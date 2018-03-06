package io.dummymaker.generator.impl.map.impl;

import io.dummymaker.generator.IGenerator;
import io.dummymaker.generator.impl.map.IMapGenerator;
import io.dummymaker.generator.impl.string.IdBigGenerator;
import io.dummymaker.generator.impl.string.IdGenerator;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 * "Default Description"
 *
 * @author GoodforGod
 * @since 06.03.2018
 */
abstract class BasicMapGenerator<K, V> implements IMapGenerator<K, V> {

    private final Object EMPTY = new Object();

    private final IGenerator keyGenerator;
    private final IGenerator valueGenerator;

    BasicMapGenerator() {
        this.keyGenerator = new IdGenerator();
        this.valueGenerator = new IdBigGenerator();
    }

    BasicMapGenerator(final IGenerator keyGenerator,
                      final IGenerator valueGenerator) {
        this.keyGenerator = keyGenerator;
        this.valueGenerator = valueGenerator;
    }


    @Override
    public Map<K, V> generate() {
        return generate(keyGenerator, valueGenerator,
                Object.class, Object.class,
                0, 10);
    }

    @Override
    public Map<K, V> generate(final IGenerator keyGenerator,
                              final IGenerator valueGenerator,
                              final Class<?> keyType,
                              final Class<?> valueType,
                              final int min,
                              final int max) {

        final Class<?> genKeyType = keyGenerator.generate().getClass();
        final Class<?> genValueType = valueGenerator.generate().getClass();

        final boolean isKeyTypeAssignable = keyType.isAssignableFrom(genKeyType);
        final boolean isKeyTypeEquals = genKeyType.equals(keyType);
        final boolean isKeyTypeObject = keyType.equals(Object.class);
        final boolean isKeyTypeString = keyType.equals(String.class);

        final boolean isValueTypeAssignable = valueType.isAssignableFrom(genValueType);
        final boolean isValueTypeEquals = genValueType.equals(valueType);
        final boolean isValueTypeObject = valueType.equals(Object.class);
        final boolean isValueTypeString = valueType.equals(String.class);

        final Map map = new HashMap<>();
        final int amount = generateRandomAmount(min, max);

        for (int i = 0; i < amount; i++) {
            final Object key = generateObject(keyGenerator, keyType,
                    isKeyTypeAssignable, isKeyTypeEquals,
                    isKeyTypeObject, isKeyTypeString);

            final Object value = generateObject(valueGenerator, valueType,
                    isValueTypeAssignable, isValueTypeEquals,
                    isValueTypeObject, isValueTypeString);

            if (key.equals(EMPTY))
                continue;

            map.put(key, value);
        }

        return map;
    }

    private Object generateObject(final IGenerator generator,
                                  final Class<?> fieldType,
                                  final boolean isTypeAssignable,
                                  final boolean isTypeEquals,
                                  final boolean isTypeObject,
                                  final boolean isTypeString) {
        if (isTypeEquals || isTypeObject) {
            return (generator.generate());
        } else if (isTypeString) {
            return (generator.generate().toString());
        } else if (isTypeAssignable) {
            return (fieldType.cast(generator.generate()));
        }
        return EMPTY;
    }

    private int generateRandomAmount(final int min,
                                     final int max) {
        final int usedMin = (min < 1) ? 1 : min;
        final int usedMax = (max < 1) ? 1 : max;

        return (usedMin >= usedMax)
                ? usedMin
                : ThreadLocalRandom.current().nextInt(min, max);
    }
}
