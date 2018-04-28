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

    // Lazy initialization
    private IPopulateFactory embeddedFreePopulateFactory;

    <T> T generateValue(final Class<? extends IGenerator> generatorClass,
                        final Class<T> valueClass,
                        final GeneratorsStorage storage) {
        if ((EmbeddedGenerator.class.equals(generatorClass)))
            return getEmbeddedFreePopulateFactory().populate(instantiate(valueClass));

        final IGenerator valueGenerator = getGenerator(generatorClass, storage);
        return generateObject(valueGenerator, valueClass);
    }

    IGenerator getGenerator(final Class<? extends IGenerator> generatorClass,
                            final GeneratorsStorage storage) {
        if(storage != null)
            return storage.getGeneratorInstance(generatorClass);
        if(generatorClass != null)
            return BasicCastUtils.instantiate(generatorClass);
        return new NullGenerator();
    }

    IPopulateFactory getEmbeddedFreePopulateFactory() {
        if(embeddedFreePopulateFactory == null)
            this.embeddedFreePopulateFactory = new GenPopulateEmbeddedFreeFactory();
        return embeddedFreePopulateFactory;
    }

    @Override
    public abstract Object generate(final Annotation annotation,
                                    final Field field,
                                    final GeneratorsStorage storage);

    @Override
    public abstract Object generate();
}