package io.dummymaker.factory;

import io.dummymaker.generator.IComplexGenerator;
import io.dummymaker.generator.IGenerator;

/**
 * Used to extend complex generator functionality by providing
 * Generators storage for performance improvement and
 * Embedded object generation
 *
 * @author GoodforGod
 * @see io.dummymaker.annotation.special.GenEmbedded
 * @see IGenFactory
 * @see IComplexGenerator
 * @since 21.07.2019
 */
public interface IGenStorage extends IGenSupplier {

    /**
     * Gets depth for target based on its parent
     * @param parent class
     * @param target class
     * @return depth
     */
    default int getDepth(Class<?> parent, Class<?> target) {
        return 1;
    }

    /**
     * Fill object with random values starting from desired depth
     *
     * @param t     objects to fill
     * @param depth from which to start filling object
     * @param <T>   object type
     * @return object with random data
     * @see IGenFactory#fill(Object)
     */
    <T> T fillByDepth(T t, int depth);

    /**
     * Returns instance of generator class
     *
     * @param generatorClass instance to get
     * @return instance of generator
     */
    IGenerator getGenerator(Class<? extends IGenerator> generatorClass);
}
