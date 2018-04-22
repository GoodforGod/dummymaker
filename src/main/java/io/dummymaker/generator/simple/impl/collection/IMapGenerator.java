package io.dummymaker.generator.simple.impl.collection;

import io.dummymaker.generator.simple.IGenerator;

import java.util.Map;

/**
 * Generate map of elements with specific key,value type
 *
 * @author GoodforGod
 * @since 06.03.2018
 */
public interface IMapGenerator<K, V> extends IGenerator<Map<K, V>> {

    /**
     * Generates map with random amount of elements,
     * between min and max
     * and specified key and value types
     *
     * @param keyGenerator generator type
     * @param valueGenerator generator type
     * @param keyType map key type
     * @param valueType map value type
     * @param min min number of elements
     * @param max max number of elements
     *
     * @return filled hash map
     */
    Map<K, V> generate(final IGenerator keyGenerator,
                       final IGenerator valueGenerator,
                       final Class<?> keyType,
                       final Class<?> valueType,
                       final int min,
                       final int max);
}
