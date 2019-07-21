package io.dummymaker.generator.complex.impl;

import io.dummymaker.factory.IComplexService;
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
 * @author GoodforGod
 * @see IComplexGenerator
 * @since 22.04.2018
 */
abstract class BasicComplexGenerator implements IComplexGenerator {

    static final int MIN_DEFAULT = 1;
    static final int MAX_DEFAULT = 10;

    static int getDesiredSize(final int min,
                              final int max,
                              final int fixed) {
        return (fixed > -1) ? fixed : BasicCollectionUtils.generateRandomSize(min, max);
    }

    boolean isGenDefault(Class<? extends IGenerator> generatorClass) {
        return IGenerator.class.equals(generatorClass);
    }

    <T> T generateValue(final Class<? extends IGenerator> generatorClass,
                        final Class<T> valueClass,
                        final IComplexService storage,
                        final int depth,
                        final int depthLimit) {
        final int parsedDepthLimit = EmbeddedGenerator.toDepth(depthLimit);
        if ((EmbeddedGenerator.class.equals(generatorClass) || NullGenerator.class.equals(generatorClass))
                && depth <= parsedDepthLimit) {
            return storage.fillWithDepth(instantiate(valueClass), depth + 1);
        }

        final IGenerator generator = (storage == null)
                ? BasicCastUtils.instantiate(generatorClass)
                : storage.getGenerator(generatorClass);

        return generateObject(generator, valueClass);
    }

    @Override
    public abstract Object generate(final Annotation annotation,
                                    final Field field,
                                    final IComplexService storage,
                                    final int depth);

    @Override
    public abstract Object generate();
}
