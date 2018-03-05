package io.dummymaker.generator.impl.collection.impl;

import io.dummymaker.generator.IGenerator;

import java.util.Collection;
import java.util.HashSet;

/**
 * "Default Description"
 *
 * @author GoodforGod
 * @since 06.03.2018
 */
public class SetGenerator extends BasicCollectionGenerator<Object> {

    public SetGenerator() {
        super();
    }

    public SetGenerator(IGenerator generator) {
        super(generator);
    }

    @Override
    public Collection<Object> generate() {
        return new HashSet<>(super.generate());
    }

    @Override
    public Collection<Object> generate(final IGenerator generator,
                                       final Class<?> fieldType,
                                       final int min,
                                       final int max) {
        return new HashSet<>(super.generate(generator, fieldType, min, max));
    }
}
