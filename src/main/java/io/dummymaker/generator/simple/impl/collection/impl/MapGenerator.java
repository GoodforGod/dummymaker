package io.dummymaker.generator.simple.impl.collection.impl;

import io.dummymaker.generator.simple.IGenerator;

/**
 * Generate map of elements with key,value of object type
 *
 * @see BasicMapGenerator
 * @see io.dummymaker.generator.simple.impl.collection.IMapGenerator
 *
 * @author GoodforGod
 * @since 06.03.2018
 */
public class MapGenerator extends BasicMapGenerator<Object, Object> {

    public MapGenerator() {
        super();
    }

    public MapGenerator(final IGenerator defaultKeyGenerator,
                        final IGenerator defaultValueGenerator) {
        super(defaultKeyGenerator, defaultValueGenerator);
    }
}
