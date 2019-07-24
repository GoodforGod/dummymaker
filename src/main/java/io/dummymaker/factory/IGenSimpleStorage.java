package io.dummymaker.factory;

import io.dummymaker.generator.simple.IGenerator;

/**
 * Used to extend complex generator functionality by providing
 * Generators storage for performance improvement and
 * Embedded object generation
 *
 * @author GoodforGod
 * @see io.dummymaker.annotation.special.GenEmbedded
 * @see IGenFactory
 * @see io.dummymaker.generator.complex.IComplexGenerator
 * @since 21.07.2019
 */
public interface IGenSimpleStorage {

    /**
     * Fill object with random values starting from desired depth
     *
     * @param t     objects to fill
     * @param depth from which to start filling object
     * @param <T>   object type
     * @return object with random data
     * @see IGenFactory#fill(Object)
     */
    <T> T fillWithDepth(T t, int depth);

    IGenerator getGenerator(Class<? extends IGenerator> generatorClass);
}
