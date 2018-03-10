package io.dummymaker.generator.impl.collection.impl;

import io.dummymaker.factory.IPopulateFactory;
import io.dummymaker.factory.impl.GenPopulateEmbeddedFreeFactory;
import io.dummymaker.generator.IGenerator;
import io.dummymaker.generator.impl.EmbeddedGenerator;
import io.dummymaker.generator.impl.collection.ICollectionGenerator;
import io.dummymaker.generator.impl.string.IdGenerator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static io.dummymaker.util.BasicCastUtils.*;

/**
 * Generate array list collection of elements with object type
 *
 * @see ICollectionGenerator
 *
 * @author GoodforGod
 * @since 06.03.2018
 */
abstract class BasicCollectionGenerator<T> implements ICollectionGenerator<T> {

    private final IGenerator generator;

    BasicCollectionGenerator() {
        this.generator = new IdGenerator();
    }

    BasicCollectionGenerator(final IGenerator generator) {
        this.generator = generator;
    }

    @Override
    public Collection<T> generate() {
        return generate(generator, Object.class, 1, 10);
    }

    @Override
    public Collection<T> generate(final IGenerator generator,
                                  final Class<?> fieldType,
                                  final int min,
                                  final int max) {
        final List list = new ArrayList<>();
        final int amount = generateRandomAmount(min, max);

        final boolean isEmbedded = (generator != null && generator.getClass().equals(EmbeddedGenerator.class));

        final IPopulateFactory embeddedPopulateFactory = (isEmbedded)
                ? new GenPopulateEmbeddedFreeFactory()
                : null;

        for (int i = 0; i < amount; i++) {
            final Object object = (isEmbedded)
                    ? embeddedPopulateFactory.populate(instanceClass(fieldType))
                    : generateObject(generator, fieldType);

            if(object.equals(EMPTY))
                return Collections.emptyList();

            list.add(object);
        }

        return list;
    }
}
