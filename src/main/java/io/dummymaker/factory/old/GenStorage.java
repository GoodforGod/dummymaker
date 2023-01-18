package io.dummymaker.factory.old;

import io.dummymaker.generator.Generator;
import io.dummymaker.generator.complex.ComplexGenerator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Used to extend complex generator functionality by providing Generators storage for performance
 * improvement and Embedded object generation
 *
 * @author Anton Kurako (GoodforGod)
 * @see GenFactory
 * @see ComplexGenerator
 * @since 21.07.2019
 */
@Deprecated
public interface GenStorage extends GenSupplier {

    /**
     * Gets depth for target based on its parent
     *
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
     * @see GenFactory#fill(Object)
     */
    @Nullable
    <T> T fillByDepth(@Nullable T t, int depth);

    /**
     * Returns instance of generator class
     *
     * @param generatorClass instance to get
     * @return instance of generator
     */
    @NotNull
    Generator<?> getGenerator(Class<? extends Generator> generatorClass);
}
