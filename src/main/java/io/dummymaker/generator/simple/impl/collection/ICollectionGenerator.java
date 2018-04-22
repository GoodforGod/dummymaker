package io.dummymaker.generator.simple.impl.collection;

import io.dummymaker.generator.simple.IGenerator;

import java.util.Collection;

/**
 * Generate collection of elements with specific type
 *
 * @author GoodforGod
 * @since 06.03.2018
 */
public interface ICollectionGenerator<T> extends IGenerator<Collection<T>> {

        /**
         * Generates collection with random amount of elements,
         * between min and max
         *
         * @param generator generator type
         * @param fieldType dummy object field type
         * @param min min number of elements
         * @param max max number of elements
         * @return array list collection
         */
        Collection<T> generate(final IGenerator generator,
                               final Class<?> fieldType,
                               final int min,
                               final int max);
}
