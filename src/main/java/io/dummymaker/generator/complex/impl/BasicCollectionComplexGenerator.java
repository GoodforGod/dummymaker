package io.dummymaker.generator.complex.impl;

import io.dummymaker.generator.IGenerator;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * "default comment"
 *
 * @author GoodforGod
 * @since 22.04.2018
 */
public abstract class BasicCollectionComplexGenerator extends BasicComplexGenerator {

    protected BasicCollectionComplexGenerator(final IGenerator defaultGenerator) {
        super(defaultGenerator);
    }

    protected Object generateList(final int amount,
                                  final Class<? extends IGenerator> valueGenerator,
                                  final Class<?> fieldClass) {

        // Firstly try to generate initial object, so we won't allocate list if not necessary
        final Object firstObject = generateValue(valueGenerator, fieldClass);
        if(firstObject == null)
            return Collections.emptyList();

        final List list = new ArrayList<>(amount);
        list.add(firstObject);
        for (int i = 0; i < amount; i++) {
            final Object object = generateValue(valueGenerator, fieldClass);
            list.add(object);
        }

        return list;
    }

    @Override
    public abstract Object generate(Annotation annotation, Class<?> fieldClass);

    @Override
    public abstract Object generate();
}
