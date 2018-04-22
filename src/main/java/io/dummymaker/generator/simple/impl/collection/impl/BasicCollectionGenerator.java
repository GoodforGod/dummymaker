package io.dummymaker.generator.simple.impl.collection.impl;

import io.dummymaker.factory.IPopulateFactory;
import io.dummymaker.factory.impl.GenPopulateEmbeddedFreeFactory;
import io.dummymaker.generator.simple.IGenerator;
import io.dummymaker.generator.simple.impl.EmbeddedGenerator;
import io.dummymaker.generator.simple.impl.collection.ICollectionGenerator;
import io.dummymaker.generator.simple.impl.string.IdGenerator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static io.dummymaker.util.BasicCastUtils.generateObject;
import static io.dummymaker.util.BasicCastUtils.instantiate;
import static io.dummymaker.util.BasicCollectionUtils.generateRandomAmount;

/**
 * Generate array list collection of elements with object type
 *
 * @see ICollectionGenerator
 *
 * @author GoodforGod
 * @since 06.03.2018
 */
abstract class BasicCollectionGenerator<T> implements ICollectionGenerator<T> {

    private IGenerator defaultGenerator = new IdGenerator();

    @Override
    public Collection<T> generate() {
        return generate(defaultGenerator, Object.class, 1, 10);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Collection<T> generate(final IGenerator generator,
                                  final Class<?> fieldType,
                                  final int min,
                                  final int max) {
        final List list = new ArrayList<>();
        final int amount = generateRandomAmount(min, max);

        final IGenerator usedGenerator = (generator == null) ? defaultGenerator : generator;
        final boolean isEmbedded = (usedGenerator.getClass().equals(EmbeddedGenerator.class));

        final IPopulateFactory embeddedPopulateFactory = (isEmbedded)
                ? new GenPopulateEmbeddedFreeFactory()
                : null;

        for (int i = 0; i < amount; i++) {
            final Object object = (isEmbedded)
                    ? embeddedPopulateFactory.populate(instantiate(fieldType))
                    : generateObject(usedGenerator, fieldType);

            if(object == null)
                return Collections.emptyList();

            list.add(object);
        }

        return list;
    }
}
