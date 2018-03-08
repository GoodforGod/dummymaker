package io.dummymaker.generator.impl.collection.impl;

import io.dummymaker.generator.IGenerator;
import io.dummymaker.generator.impl.collection.ICollectionGenerator;
import io.dummymaker.generator.impl.string.IdGenerator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static io.dummymaker.util.BasicCastUtils.generateObject;
import static io.dummymaker.util.BasicCastUtils.generateRandomAmount;

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
        final Class<?> genType = generator.generate().getClass();

        final boolean isTypeAssignable = fieldType.isAssignableFrom(genType);
        final boolean isTypeEquals = genType.equals(fieldType);
        final boolean isTypeObject = fieldType.equals(Object.class);
        final boolean isTypeString = fieldType.equals(String.class);

        final List list = new ArrayList<>();
        final int amount = generateRandomAmount(min, max);

        for (int i = 0; i < amount; i++) {
            list.add(generateObject(generator, genType,
                    isTypeAssignable, isTypeEquals,
                    isTypeObject, isTypeString));
        }

        return list;
    }
}
