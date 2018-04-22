package io.dummymaker.generator.complex.impl;

import io.dummymaker.factory.IPopulateFactory;
import io.dummymaker.factory.impl.GenPopulateEmbeddedFreeFactory;
import io.dummymaker.generator.IGenerator;
import io.dummymaker.generator.complex.IComplexGenerator;
import io.dummymaker.generator.impl.EmbeddedGenerator;

import java.lang.annotation.Annotation;

import static io.dummymaker.util.BasicCastUtils.generateObject;
import static io.dummymaker.util.BasicCastUtils.instantiate;

/**
 * "default comment"
 *
 * @author GoodforGod
 * @since 22.04.2018
 */
public abstract class BasicComplexGenerator implements IComplexGenerator {

    private final IGenerator defaultGenerator;

    // Lazy initialization and
    private IGenerator lastInstGenerator;
    private IPopulateFactory embeddedFreePopulateFactory;

    protected BasicComplexGenerator(final IGenerator defaultGenerator) {
        this.defaultGenerator = defaultGenerator;
    }

    <T> T generateValue(final Class<? extends IGenerator> annotationGenerator,
                        final Class<T> fieldClass) {
        final IGenerator valueGenerator = getGenerator(annotationGenerator);

        final boolean isEmbedded = (valueGenerator.getClass().equals(EmbeddedGenerator.class));
        return (isEmbedded)
                ? getEmbeddedFreePopulateFactory().populate(instantiate(fieldClass))
                : generateObject(valueGenerator, fieldClass);
    }

    protected IGenerator getGenerator(final Class<? extends IGenerator> annotationGenerator) {
        final IGenerator valueGenerator = (lastInstGenerator != null && lastInstGenerator.getClass().equals(annotationGenerator))
                ? lastInstGenerator
                : instantiate(annotationGenerator);

        // Remember last instantiated generator
        this.lastInstGenerator = valueGenerator;

        return (valueGenerator == null)
                ? getDefaultGenerator()
                : valueGenerator;
    }

    protected IGenerator getDefaultGenerator() {
        return defaultGenerator;
    }

    protected IPopulateFactory getEmbeddedFreePopulateFactory() {
        if(embeddedFreePopulateFactory == null)
            this.embeddedFreePopulateFactory = new GenPopulateEmbeddedFreeFactory();
        return embeddedFreePopulateFactory;
    }

    @Override
    public abstract Object generate(final Annotation annotation,
                                    final Class<?> fieldClass);

    @Override
    public abstract Object generate();
}
