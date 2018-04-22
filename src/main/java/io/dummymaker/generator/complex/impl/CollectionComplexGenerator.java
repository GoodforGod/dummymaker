package io.dummymaker.generator.complex.impl;

import io.dummymaker.generator.IGenerator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * "default comment"
 *
 * @author GoodforGod
 * @since 22.04.2018
 */
abstract class CollectionComplexGenerator extends BasicComplexGenerator {

    CollectionComplexGenerator(final IGenerator defaultGenerator) {
        super(defaultGenerator);
    }

    <T> List<T> generateList(final int amount,
                             final Class<? extends IGenerator> valueGenerator,
                             final Class<T> fieldClass) {

        // Firstly try to generate initial object, so we won't allocate list if not necessary
        final T initial = generateValue(valueGenerator, fieldClass);
        if(initial == null)
            return Collections.emptyList();

        final List<T> list = new ArrayList<>(amount);
        list.add(initial);
        for (int i = 0; i < amount; i++) {
            final T t = generateValue(valueGenerator, fieldClass);
            list.add(t);
        }

        return list;
    }

    @Override
    public abstract Object generate(final Annotation annotation,
                                    final Field field);

    @Override
    public abstract Object generate();
}
