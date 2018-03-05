package io.dummymaker.generator.impl.collection.impl;

import io.dummymaker.generator.IGenerator;

/**
 * Generate list collection of elements with object type
 *
 * @author GoodforGod
 * @since 06.03.2018
 */
public class ListGenerator extends BasicCollectionGenerator<Object> {

    public ListGenerator() {
        super();
    }

    public ListGenerator(IGenerator generator) {
        super(generator);
    }
}
