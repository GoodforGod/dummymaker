package io.dummymaker.generator.impl.map.impl;

import io.dummymaker.generator.IGenerator;

/**
 * "Default Description"
 *
 * @author GoodforGod
 * @since 06.03.2018
 */
public class MapGenerator extends BasicMapGenerator<Object, Object> {

    public MapGenerator() {
        super();
    }

    public MapGenerator(final IGenerator keyGenerator,
                        final IGenerator valueGenerator) {
        super(keyGenerator, valueGenerator);
    }
}
