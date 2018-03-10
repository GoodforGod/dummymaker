package io.dummymaker.factory.impl;

import io.dummymaker.factory.IGenerateFactory;
import io.dummymaker.generator.IGenerator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.logging.Logger;

/**
 * Basic core implementation of generate factory with suitable find methods implementation
 *
 * @see IGenerateFactory
 *
 * @author GoodforGod
 * @since 08.03.2018
 */
public abstract class BasicGenerateFactory<T extends IGenerator> implements IGenerateFactory<T> {

    protected static final Logger logger = Logger.getLogger(BasicGenerateFactory.class.getName());

    /**
     * Annotation provider for generate factory
     */
    protected final Class<? extends Annotation> suitableAnnotation;

    /**
     * Build generate factory basic impl
     *
     * @param suitableAnnotation annotation provider type
     * @throws NullPointerException if annotation is nullable
     */
    public BasicGenerateFactory(final Class<? extends Annotation> suitableAnnotation) {
        if(suitableAnnotation == null)
            throw new NullPointerException("Annotation can not be nullable");

        this.suitableAnnotation = suitableAnnotation;
    }

    @Override
    public abstract Object generate(final Field field,
                                    final Annotation annotation);

    @Override
    public abstract Object generate(final Field field,
                                    final Annotation annotation,
                                    final T generator);
}
