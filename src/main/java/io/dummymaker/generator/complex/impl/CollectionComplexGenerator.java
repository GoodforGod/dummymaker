package io.dummymaker.generator.complex.impl;

import io.dummymaker.factory.IComplexService;
import io.dummymaker.generator.simple.IGenerator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;

/**
 * Basic collection complex generator implementation
 * Can be used by other collection complex generators
 *
 * @author GoodforGod
 * @see io.dummymaker.generator.complex.IComplexGenerator
 * @since 22.04.2018
 */
abstract class CollectionComplexGenerator extends BasicComplexGenerator {

    @SuppressWarnings("unchecked")
    Collection genCollection(final int size,
                             final Collection<?> collection,
                             final Class<? extends IGenerator> valueGenerator,
                             final Class<?> fieldClass,
                             final IComplexService storage,
                             final int depth,
                             final int maxDepth) {

        // Firstly try to generate initial object, so we won't allocate list if not necessary
        final Object initial = generateValue(valueGenerator, fieldClass, storage, depth, maxDepth);
        if (initial == null) {
            if (collection == null || collection.getClass().isAssignableFrom(List.class)) {
                return Collections.emptyList();
            } else if (collection.getClass().isAssignableFrom(Set.class)) {
                return Collections.emptySet();
            } else if (collection.getClass().isAssignableFrom(Queue.class)) {
                return new ArrayDeque<>();
            }
            return Collections.emptyList();
        }

        final Collection list = (collection == null) ? new ArrayList<>(size) : collection;
        list.add(initial);
        for (int i = 1; i < size; i++) {
            final Object t = generateValue(valueGenerator, fieldClass, storage, depth, maxDepth);
            list.add(t);
        }

        return list;
    }

    @Override
    public abstract Object generate(final Annotation annotation,
                                    final Field field,
                                    final IComplexService storage,
                                    final int depth);

    @Override
    public abstract Object generate();
}
