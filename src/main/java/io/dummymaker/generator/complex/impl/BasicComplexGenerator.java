package io.dummymaker.generator.complex.impl;

import io.dummymaker.annotation.special.GenEmbedded;
import io.dummymaker.container.impl.GeneratorsStorage;
import io.dummymaker.factory.IPopulateEmbeddedFactory;
import io.dummymaker.factory.impl.GenSimpleFactory;
import io.dummymaker.generator.complex.IComplexGenerator;
import io.dummymaker.generator.simple.IGenerator;
import io.dummymaker.generator.simple.impl.EmbeddedGenerator;
import io.dummymaker.generator.simple.impl.NullGenerator;
import io.dummymaker.util.BasicCastUtils;
import io.dummymaker.util.BasicCollectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import static io.dummymaker.util.BasicCastUtils.generateObject;
import static io.dummymaker.util.BasicCastUtils.instantiate;

/**
 * Basic complex generator implementation
 * Can be used by other Complex generators
 * Providing basic methods, to build new generators on top of this one
 *
 * @see IComplexGenerator
 *
 * @author GoodforGod
 * @since 22.04.2018
 */
abstract class BasicComplexGenerator implements IComplexGenerator {

    static final int MIN_COUNT_DEFAULT = 1;
    static final int MAX_COUNT_DEFAULT = 10;

    // Lazy initialization
    private IPopulateEmbeddedFactory embeddedFreePopulateFactory;

    boolean isGenDefault(Class<? extends IGenerator> generatorClass) {
        return IGenerator.class.equals(generatorClass);
    }

    <T> T generateValue(final Class<? extends IGenerator> generatorClass,
                        final Class<T> valueClass,
                        final GeneratorsStorage storage,
                        final int depth) {
        return generateValue(generatorClass, valueClass, storage, depth, 1);
    }

    <T> T generateValue(final Class<? extends IGenerator> generatorClass,
                        final Class<T> valueClass,
                        final GeneratorsStorage storage,
                        final int depth,
                        final int depthLimit) {
        final int realDepthLimit = (depthLimit > GenEmbedded.MAX) ? GenEmbedded.MAX : (depthLimit < 1 ? 1 : depthLimit);
        if ((EmbeddedGenerator.class.equals(generatorClass) || NullGenerator.class.equals(generatorClass))
                && depth <= realDepthLimit) {
            return getEmbeddedFreePopulateFactory().populate(instantiate(valueClass), depth + 1);
        }

        final IGenerator generator = getGenerator(generatorClass, storage);
        return generateObject(generator, valueClass);
    }

    IGenerator getGenerator(final Class<? extends IGenerator> generatorClass,
                            final GeneratorsStorage storage) {
        if (generatorClass == null)
            return new NullGenerator();

        return (storage != null)
                ? storage.getGenInstance(generatorClass)
                : BasicCastUtils.instantiate(generatorClass);
    }

    /**
     * Use with CAUTION
     * You could fall into recursion if not handling it like here
     *
     * @return embeddedFreePopulateFactory
     */
    IPopulateEmbeddedFactory getEmbeddedFreePopulateFactory() {
        if (embeddedFreePopulateFactory == null) {
            this.embeddedFreePopulateFactory = new GenSimpleFactory();
        }
        return embeddedFreePopulateFactory;
    }

    static int genRandomSize(final int min,
                             final int max,
                             final int fixed) {
        return (fixed > -1) ? fixed : BasicCollectionUtils.generateRandomSize(min, max);
    }

    @Override
    public abstract Object generate(final Annotation annotation,
                                    final Field field,
                                    final GeneratorsStorage storage,
                                    final int depth);

    @Override
    public abstract Object generate();
}
