package io.dummymaker.generator.complex.impl;

import io.dummymaker.factory.IPopulateFactory;
import io.dummymaker.factory.impl.GenPopulateEmbeddedFreeFactory;
import io.dummymaker.generator.complex.IComplexGenerator;
import io.dummymaker.generator.simple.IGenerator;
import io.dummymaker.generator.simple.impl.EmbeddedGenerator;

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

    private final IGenerator defaultGenerator;

    // Lazy initialization
    private IGenerator lastInstGenerator;
    private IPopulateFactory embeddedFreePopulateFactory;

    BasicComplexGenerator(final IGenerator defaultGenerator) {
        this.defaultGenerator = defaultGenerator;
    }

    <T> T generateValue(final Class<? extends IGenerator> annotationGenerator,
                        final Class<T> valueClass) {
        final IGenerator valueGenerator = getGenerator(annotationGenerator);

        final boolean isEmbedded = (valueGenerator.getClass().equals(EmbeddedGenerator.class));
        return (isEmbedded)
                ? getEmbeddedFreePopulateFactory().populate(instantiate(valueClass))
                : generateObject(valueGenerator, valueClass);
    }

    IGenerator getGenerator(final Class<? extends IGenerator> annotationGenerator) {
        final IGenerator valueGenerator = (lastInstGenerator != null && lastInstGenerator.getClass().equals(annotationGenerator))
                ? lastInstGenerator
                : instantiate(annotationGenerator);

        // Remember last instantiated generator
        this.lastInstGenerator = valueGenerator;

        return (valueGenerator == null)
                ? getDefaultGenerator()
                : valueGenerator;
    }

    IGenerator getDefaultGenerator() {
        return defaultGenerator;
    }

    IPopulateFactory getEmbeddedFreePopulateFactory() {
        if(embeddedFreePopulateFactory == null)
            this.embeddedFreePopulateFactory = new GenPopulateEmbeddedFreeFactory();
        return embeddedFreePopulateFactory;
    }

    @Override
    public abstract Object generate(final Annotation annotation,
                                    final Field field);

    @Override
    public abstract Object generate();
}
