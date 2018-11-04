package io.dummymaker.generator.complex.impl;

import io.dummymaker.container.impl.GeneratorsStorage;
import io.dummymaker.factory.IPopulateFactory;
import io.dummymaker.factory.impl.GenPopulateEmbeddedFreeFactory;
import io.dummymaker.generator.complex.IComplexGenerator;
import io.dummymaker.generator.simple.IGenerator;
import io.dummymaker.generator.simple.impl.EmbeddedGenerator;
import io.dummymaker.generator.simple.impl.NullGenerator;
import io.dummymaker.util.BasicCastUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.concurrent.ThreadLocalRandom;

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

    static final int MIN_DEFAULT = 1;
    static final int MAX_DEFAULT = 10;

    // Lazy initialization
    private IPopulateFactory embeddedFreePopulateFactory;

    boolean isGenDefault(Class<? extends IGenerator> tClass) {
        return IGenerator.class.equals(tClass);
    }

    <T> T generateValue(final Class<? extends IGenerator> valueGeneratorClass,
                        final Class<T> valueClass,
                        final GeneratorsStorage storage) {
        if ((EmbeddedGenerator.class.equals(valueGeneratorClass)))
            return getEmbeddedFreePopulateFactory().populate(instantiate(valueClass));

        final IGenerator valueGenerator = getGenerator(valueGeneratorClass, storage);
        return generateObject(valueGenerator, valueClass);
    }

    IGenerator getGenerator(final Class<? extends IGenerator> generatorClass,
                            final GeneratorsStorage storage) {
        if (storage != null)
            return storage.getGenInstance(generatorClass);
        if (generatorClass != null)
            return BasicCastUtils.instantiate(generatorClass);
        return new NullGenerator();
    }

    IPopulateFactory getEmbeddedFreePopulateFactory() {
        if (embeddedFreePopulateFactory == null)
            this.embeddedFreePopulateFactory = new GenPopulateEmbeddedFreeFactory();
        return embeddedFreePopulateFactory;
    }

    static int genRandomSize(final int min,
                             final int max) {
        final int usedMin = (min < 1) ? 0 : min;
        final int usedMax = (max < 1) ? 0 : max;
        return (usedMin >= usedMax) ? usedMin : ThreadLocalRandom.current().nextInt(usedMin, usedMax);
    }

    static int genRandomSize(final int min,
                             final int max,
                             final int fixed) {
        return (fixed > -1) ? fixed : genRandomSize(min, max);
    }

    @Override
    public abstract Object generate(final Annotation annotation,
                                    final Field field,
                                    final GeneratorsStorage storage);

    @Override
    public abstract Object generate();
}
