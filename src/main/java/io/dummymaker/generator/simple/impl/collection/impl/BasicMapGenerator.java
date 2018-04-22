package io.dummymaker.generator.simple.impl.collection.impl;

import io.dummymaker.factory.IPopulateFactory;
import io.dummymaker.factory.impl.GenPopulateEmbeddedFreeFactory;
import io.dummymaker.generator.simple.IGenerator;
import io.dummymaker.generator.simple.impl.EmbeddedGenerator;
import io.dummymaker.generator.simple.impl.collection.IMapGenerator;
import io.dummymaker.generator.simple.impl.string.IdBigGenerator;
import io.dummymaker.generator.simple.impl.string.IdGenerator;

import java.util.HashMap;
import java.util.Map;

import static io.dummymaker.util.BasicCastUtils.generateObject;
import static io.dummymaker.util.BasicCastUtils.instantiate;
import static io.dummymaker.util.BasicCollectionUtils.generateRandomAmount;

/**
 * Generate map of elements with key,value of object type
 *
 * @see io.dummymaker.generator.simple.impl.collection.IMapGenerator
 *
 * @author GoodforGod
 * @since 06.03.2018
 */
abstract class BasicMapGenerator<K, V> implements IMapGenerator<K, V> {

    private IGenerator defaultKeyGenerator = new IdGenerator();
    private IGenerator defaultValueGenerator = new IdBigGenerator();

    @Override
    public Map<K, V> generate() {
        return generate(defaultKeyGenerator, defaultValueGenerator,
                Object.class, Object.class,
                1, 10);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<K, V> generate(final IGenerator keyGenerator,
                              final IGenerator valueGenerator,
                              final Class<?> keyType,
                              final Class<?> valueType,
                              final int min,
                              final int max) {

        final Map map = new HashMap<>();
        final int amount = generateRandomAmount(min, max);

        final IGenerator usedKeyGenerator   = (keyGenerator == null) ? defaultKeyGenerator : keyGenerator;
        final IGenerator usedValueGenerator = (valueGenerator == null) ? defaultValueGenerator : valueGenerator;

        final boolean isKeyEmbedded = usedKeyGenerator.getClass().equals(EmbeddedGenerator.class);
        final boolean isValueEmbedded = usedValueGenerator.getClass().equals(EmbeddedGenerator.class);

        final IPopulateFactory embeddedPopulateFactory = (isKeyEmbedded || isValueEmbedded)
                ? new GenPopulateEmbeddedFreeFactory()
                : null;

        for (int i = 0; i < amount; i++) {
            final Object key = (isKeyEmbedded)
                    ? embeddedPopulateFactory.populate(instantiate(keyType))
                    : generateObject(keyGenerator, keyType);

            final Object value = (isValueEmbedded)
                    ? embeddedPopulateFactory.populate(instantiate(valueType))
                    : generateObject(valueGenerator, valueType);

            map.put(key, value);
        }

        return map;
    }
}
